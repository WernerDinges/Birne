package core.level.skeletonGraph.utils

import core.level.skeletonGraph.SkeletonEdge

fun SkeletonEdge.isVertical() =
    this.start.first == this.end.first