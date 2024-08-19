package core.level.skeletonGraph.utils

import core.level.skeletonGraph.LevelSkeleton.AIR
import core.level.skeletonGraph.LevelSkeleton.WALL

fun drillHoles(primordial: Array<Array<Int>>, size: Pair<Int, Int>) {
    for (y in 2..<size.second - 1) {
        var gapStart: Int? = null
        var gapEnd: Int? = null

        for (x in 1..<size.first - 1) {

            if (primordial[y][x] == WALL && primordial[y][x - 1] == AIR) {
                gapStart = x
                gapEnd = x
            }

            if (gapStart != null && primordial[y][x] == WALL)
                gapEnd = x

            if (primordial[y][x] == AIR) {
                if (
                    gapStart != null && gapEnd != null && gapEnd - gapStart < 4 &&
                    ((1..2).shuffled()[0] == AIR || primordial[y - 1][x - 1] == AIR)
                ) {
                    for (i in gapStart..gapEnd)
                        primordial[y][i] = AIR
                }

                gapStart = null
                gapEnd = null
            }

        }
    }
}