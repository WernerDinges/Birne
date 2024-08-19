package core.level.skeletonGraph.utils

import Birne
import core.game.Game
import core.level.skeletonGraph.SkeletonEdge
import kotlin.math.pow

fun generateLinEnemyWeights(
    longPath: List<SkeletonEdge>,
    primordial: Array<Array<Int>>,
    difficulty: Int
) = mutableListOf<Int>().apply {
    val n = longPath.filter { (start, end) ->
        start.second == end.second && isSafeToGenerateEnemy(primordial, longPath, SkeletonEdge(start, end))
    }.size
    val factor = 0.25f * (1 + (difficulty - 1) / 6f)

    for(i in 0 ..< n)
        add((100 * (factor.pow(i))).toInt())
}.shuffled()