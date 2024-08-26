package core.generationEngine.rumble

import core.generationEngine.EngineScope
import core.generationEngine.common.Limb
import core.generationEngine.common.Limbs
import core.level.TileID.AIR
import core.level.TileID.WALL
import kotlin.random.Random

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