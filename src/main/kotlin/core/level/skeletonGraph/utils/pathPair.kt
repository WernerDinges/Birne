package core.level.skeletonGraph.utils

import core.level.skeletonGraph.SkeletonEdge

fun pathPair(
    longPath: List<SkeletonEdge>,
    i: Int,
    verticalStreak: Int,
) = setOf(
    longPath[i].start.second, longPath[i].end.second,
    longPath[i - verticalStreak + 1].start.second, longPath[i - verticalStreak + 1].end.second
)