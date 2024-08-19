package core.level.skeletonGraph.utils

import core.level.skeletonGraph.LevelSkeleton.AIR
import core.level.skeletonGraph.LevelSkeleton.WALL
import core.level.skeletonGraph.SkeletonEdge
import kotlin.math.max
import kotlin.math.min

fun blankPrimordial(size: Pair<Int, Int>, limbs: Map<SkeletonEdge, Boolean>): Array<Array<Int>> {
    val primordial = Array(size.second) { Array(size.first) { WALL } }
    // Connect nodes
    for(limb in limbs)
        if(limb.value)
            if(limb.key.start.first == limb.key.end.first)
                for(y in min(limb.key.start.second, limb.key.end.second)..max(limb.key.start.second, limb.key.end.second))
                    primordial[y][limb.key.start.first] = AIR
            else
                for(x in min(limb.key.start.first, limb.key.end.first)..max(limb.key.start.first, limb.key.end.first))
                    primordial[limb.key.start.second][x] = AIR

    // Make horizontal tunnels wider
    for(y in 2 ..< size.second-1)
        for(x in 1 ..< size.first-1)
            if(primordial[y][x] == AIR && primordial[y-1][x] == WALL && primordial[y-2][x] == WALL)
                primordial[y-1][x] = AIR

    return primordial
}