package core.level.skeletonGraph.utils

import core.level.skeletonGraph.SkeletonEdge
import core.level.skeletonGraph.SkeletonPath

fun longestPath(graph: Map<SkeletonEdge, Boolean>): SkeletonPath {
    var longest = SkeletonPath(emptyList(), emptySet())

    fun dfs(current: Pair<Int, Int>, path: SkeletonPath) {
        if(path.edges.size > longest.edges.size) {
            longest = path
        }
        for((edge, exists) in graph)
            if(exists) {
                val nextNode = if(edge.start == current) edge.end else if(edge.end == current) edge.start else continue

                if(nextNode !in path.visited)
                    dfs(nextNode, SkeletonPath(path.edges + edge, path.visited + nextNode))

            }

    }

    for((edge, exists) in graph)
        if(exists) {
            dfs(edge.start, SkeletonPath(listOf(edge), setOf(edge.start, edge.end)))
            dfs(edge.end, SkeletonPath(listOf(edge), setOf(edge.start, edge.end)))
        }

    return longest
}