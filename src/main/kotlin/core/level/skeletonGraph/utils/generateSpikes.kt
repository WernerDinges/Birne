package core.level.skeletonGraph.utils

import Birne
import core.game.Game
import core.level.skeletonGraph.LevelSkeleton.AIR
import core.level.skeletonGraph.LevelSkeleton.SPIKE
import core.level.skeletonGraph.LevelSkeleton.WALL
import core.level.skeletonGraph.SkeletonEdge
import kotlin.math.exp
import kotlin.random.Random.Default.nextInt

fun generateSpikes(
    primordial: Array<Array<Int>>,
    limbs: Map<SkeletonEdge, Boolean>,
    difficulty: Int
) {
    for ((limb, exists) in limbs)
        if (
            exists && limb.start.second == limb.end.second &&
            nextInt(100) + 1 <= 10 + 20 * (1 - exp(-0.5f * difficulty))
        ) {
            val (min, max) = listOf(limb.start.first, limb.end.first).sorted()
            if (
                primordial[limb.start.second + 1][(min..max).average().toInt()] == WALL &&
                primordial[limb.start.second - 1][(min..max).average().toInt()] == AIR
            ) {
                for (x in (min + 1)..<max)
                    if (x - 2 >= 0)
                        if (
                            (primordial[limb.start.second][x - 1] != SPIKE ||
                                    primordial[limb.start.second][x - 2] != SPIKE) &&
                            primordial[limb.start.second + 1][x - 2] != AIR &&
                            nextInt(14) < difficulty + 7
                        )
                            primordial[limb.start.second][x] = SPIKE
            }
        }
}