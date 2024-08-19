package core.level.skeletonGraph

data class SkeletonPath(
    val edges: List<SkeletonEdge>,
    val visited: Set<Pair<Int, Int>>
)