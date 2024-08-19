package core.level.skeletonGraph.utils

import core.level.skeletonGraph.SkeletonEdge

fun generateLimbs(verticals: List<Int>, horizontals: List<Int>): Map<SkeletonEdge, Boolean> {
    val limbs = mutableMapOf<SkeletonEdge, Boolean>()

    fun random75() = (1..4).shuffled()[0] in 1..3
    fun random25() = (1..4).shuffled()[0] == 1

    for(y in verticals.indices) for(x in horizontals.indices) {

        // Right
        if(x+1 <= horizontals.lastIndex)
            SkeletonEdge(horizontals[x] to verticals[y], horizontals[x+1] to verticals[y]).apply {
                if(this !in limbs.keys) limbs += this to random75() }
        // Top
        if(y-1 >= 0)
            SkeletonEdge(horizontals[x] to verticals[y], horizontals[x] to verticals[y-1]).apply {
                if(this !in limbs.keys) limbs += this to random25() }
        // Left
        if(x-1 >= 0)
            SkeletonEdge(horizontals[x] to verticals[y], horizontals[x-1] to verticals[y]).apply {
                if(this !in limbs.keys) limbs += this to random75() }
        // Bottom
        if(y+1 <= verticals.lastIndex)
            SkeletonEdge(horizontals[x] to verticals[y], horizontals[x] to verticals[y+1]).apply {
                if(this !in limbs.keys) limbs += this to random25() }

    }

    return limbs
}