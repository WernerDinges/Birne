package core.level.skeletonGraph.utils

import core.level.skeletonGraph.LevelSkeleton.AIR
import core.level.skeletonGraph.LevelSkeleton.LAMP
import core.level.skeletonGraph.LevelSkeleton.LEFT_MOSS1
import core.level.skeletonGraph.LevelSkeleton.LEFT_MOSS2
import core.level.skeletonGraph.LevelSkeleton.LEFT_WOOD
import core.level.skeletonGraph.LevelSkeleton.MOSS1
import core.level.skeletonGraph.LevelSkeleton.MOSS2
import core.level.skeletonGraph.LevelSkeleton.MOSS3
import core.level.skeletonGraph.LevelSkeleton.RIGHT_MOSS1
import core.level.skeletonGraph.LevelSkeleton.RIGHT_MOSS2
import core.level.skeletonGraph.LevelSkeleton.RIGHT_WOOD
import core.level.skeletonGraph.LevelSkeleton.WALL
import kotlin.random.Random.Default.nextBoolean
import kotlin.random.Random.Default.nextInt

fun putDecorations(
    primordial: Array<Array<Int>>
) {
    for (y in primordial.indices)
        for (x in primordial[0].indices) {
            if (primordial[y][x] != AIR)
                continue

            if (primordial[y][x - 1] == WALL && primordial[y - 1][x] == WALL && nextBoolean()) {
                primordial[y][x] = setOf(LEFT_MOSS1, LEFT_MOSS2, LEFT_WOOD).random()
                continue
            }
            if (primordial[y][x + 1] == WALL && primordial[y - 1][x] == WALL && nextBoolean()) {
                primordial[y][x] = setOf(RIGHT_MOSS1, RIGHT_MOSS2, RIGHT_WOOD).random()
                continue
            }
            if (primordial[y - 1][x] == WALL && nextInt(0, 10) in 0..2)
                primordial[y][x] = setOf(MOSS1, MOSS2, MOSS3, LAMP).random()
        }
}