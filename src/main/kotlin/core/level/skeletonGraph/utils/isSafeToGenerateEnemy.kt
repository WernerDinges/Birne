package core.level.skeletonGraph.utils

import core.level.skeletonGraph.LevelSkeleton.WALL
import core.level.skeletonGraph.SkeletonEdge
import core.level.skeletonGraph.SkeletonPath

fun isSafeToGenerateEnemy(
    primordial: Array<Array<Int>>,
    longPath: List<SkeletonEdge>,
    edge: SkeletonEdge
): Boolean {
    val x = (edge.start.first + edge.end.first) / 2
    val y = edge.start.second

    return primordial[y + 1][x] == WALL
        && primordial[y - 1][x] != WALL
        && edge.start != longPath.first().start
}