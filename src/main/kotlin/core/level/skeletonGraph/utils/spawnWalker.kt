package core.level.skeletonGraph.utils

import core.entity.enemy.Walker
import core.level.skeletonGraph.SkeletonPath

fun spawnWalker(
    minX: Int, maxX: Int,
    levelY: Int,
    playerStartX: Int
) = Walker(
    x = ((minX + 1)..<maxX).average().toFloat(),
    y = levelY.toFloat() - 0.01f,
    isMirrored = minX < playerStartX
)