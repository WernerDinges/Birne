package core.level.skeletonGraph.utils

import core.entity.collectable.Collectable
import core.entity.collectable.Key
import core.game.Game
import core.level.Level
import core.level.skeletonGraph.LevelSkeleton.FINISH_CLOSED
import core.level.skeletonGraph.LevelSkeleton.FINISH_OPEN
import core.level.skeletonGraph.LevelSkeleton.LADDER
import core.level.skeletonGraph.LevelSkeleton.PLATFORM
import core.level.skeletonGraph.LevelSkeleton.SPIKE
import core.level.skeletonGraph.LevelSkeleton.START
import core.level.skeletonGraph.LevelSkeleton.VINE
import core.level.skeletonGraph.LevelSkeleton.WALL
import core.level.skeletonGraph.SkeletonEdge
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random.Default.nextBoolean

fun tryToCloseDoor(
    finish: Pair<Int, Int>,
    primordial: Array<Array<Int>>,
    limbs: List<SkeletonEdge>,
    collectables: MutableList<Collectable>,
    removeFromCollectables: (x: Float, y: Float) -> Unit
) {
    val closed = nextBoolean()
    if(closed) {

        val prohibited = setOf(WALL, START, FINISH_OPEN, FINISH_CLOSED, LADDER, VINE, SPIKE, PLATFORM)

        close@for(limb in limbs.shuffled()) {
            val path = findPath(finish, limb.end, limbs) ?: continue
            val l = path.last()

            // Vertical
            if(l.isVertical())
                for(y in min(l.start.second, l.end.second)..max(l.start.second, l.end.second))
                    if(primordial[y][l.end.first] !in prohibited) {
                        primordial[finish.second][finish.first] = FINISH_CLOSED

                        collectables += Key(l.end.first.toFloat(), y.toFloat()) {
                            removeFromCollectables(l.end.first.toFloat(), y.toFloat())
                            primordial[finish.second][finish.first] = FINISH_OPEN
                        }

                        break@close
                    }

            // Horizontal
            else
                for(x in min(l.start.first, l.end.first)..max(l.start.first, l.end.first))
                    if(primordial[l.end.second][x] !in prohibited) {
                        primordial[finish.second][finish.first] = FINISH_CLOSED

                        collectables += Key(x.toFloat(), l.end.second.toFloat()) {
                            removeFromCollectables(x.toFloat(), l.end.second.toFloat())
                            primordial[finish.second][finish.first] = FINISH_OPEN
                        }

                        break@close

                    } else if(primordial[l.end.second-1][x] !in prohibited) {
                        primordial[finish.second][finish.first] = FINISH_CLOSED

                        collectables += Key(x.toFloat(), (l.end.second-1).toFloat()) {
                            removeFromCollectables(x.toFloat(), (l.end.second-1).toFloat())
                            primordial[finish.second][finish.first] = FINISH_OPEN
                        }

                        break@close
                    }
        }

    }
}

private fun findPath(
    from: Pair<Int, Int>,
    to: Pair<Int, Int>,
    edges: List<SkeletonEdge>
): List<SkeletonEdge>? {
    // Map to store the connections from each node
    val graph = mutableMapOf<Pair<Int, Int>, MutableList<SkeletonEdge>>()

    // Build the graph from the list of edges
    for (edge in edges) {
        graph.computeIfAbsent(edge.start) { mutableListOf() }.add(edge)
        graph.computeIfAbsent(edge.end) { mutableListOf() }.add(edge)
    }

    // Queue for BFS (each item is a path we're exploring)
    val queue = ArrayDeque<List<SkeletonEdge>>()

    // Set to keep track of visited nodes
    val visited = mutableSetOf<Pair<Int, Int>>()

    // Start with the initial node (empty path)
    queue.addLast(emptyList())

    while (queue.isNotEmpty()) {
        val currentPath = queue.removeFirst()
        val currentNode = currentPath.lastOrNull()?.end ?: from

        // Check if we've reached the destination
        if (currentNode == to) {
            return currentPath.ifEmpty { null }
        }

        // Mark the node as visited
        if (!visited.add(currentNode)) continue

        // Explore the neighbors
        for (edge in graph[currentNode] ?: emptyList()) {
            val nextNode = if (edge.start == currentNode) edge.end else edge.start

            if (!visited.contains(nextNode)) {
                queue.addLast(currentPath + edge)
            }
        }
    }

    // No path found
    return null
}