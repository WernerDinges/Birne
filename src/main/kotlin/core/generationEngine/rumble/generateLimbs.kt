package core.generationEngine.rumble

import core.generationEngine.EngineScope
import core.generationEngine.common.Limb
import core.generationEngine.common.Limbs
import core.level.TileID.AIR
import core.level.TileID.WALL
import kotlin.random.Random

/**
 * Generates and populates limbs for the current level configuration within the engine scope.
 * This method slices the level map into horizontal and vertical segments,
 * then creates and connects limbs both horizontally and vertically based on randomized conditions.
 * The limbs are stored in a `Limbs` object, which includes a list of all limbs and their connections.
 *
 * @return A `Limbs` object containing the generated limbs and their connections for the level.
 */
fun EngineScope.generateLimbs(): Limbs {
    val hors = sliceLevelMap(level.mapSize.first, 1)
    val vers = sliceLevelMap(level.mapSize.second, 2)
    val limbs = Limbs()

    for(i in hors.indices) for(j in vers.indices) {

        val currentNode = hors[i] to vers[j]

        // Horizontal limb (to the right)
        if(i < hors.lastIndex && Random.nextDouble() <= .75) {
                val rightNode = hors[i+1] to vers[j]
                val limb = Limb(currentNode, rightNode, isVertical = false, false)
                limbs.list += limb
                limbs.connections.getOrPut(currentNode) { mutableSetOf() } += rightNode
                limbs.connections.getOrPut(rightNode) { mutableSetOf() } += currentNode

                for(x in currentNode.first..rightNode.first) {
                    level.tileSkeleton[currentNode.second][x] = AIR

                    if(level.tileSkeleton[currentNode.second-1][x] == WALL && level.tileSkeleton[currentNode.second-2][x] == WALL) {
                        level.tileSkeleton[currentNode.second-1][x] = AIR
                        // Adding to decoratables
                        decoratables.hollow += Pair(x, currentNode.second-1)
                    }
                }
            }

            // Vertical limb (downwards)
            if(j < vers.lastIndex && Random.nextDouble() <= .25) {
                val downNode = hors[i] to vers[j+1]
                val limb = Limb(currentNode, downNode, isVertical = true, false)
                limbs.list += limb
                limbs.connections.getOrPut(currentNode) { mutableSetOf() } += downNode
                limbs.connections.getOrPut(downNode) { mutableSetOf() } += currentNode

                for(y in currentNode.second..downNode.second)
                    level.tileSkeleton[y][currentNode.first] = AIR
            }
    }

    return limbs
}