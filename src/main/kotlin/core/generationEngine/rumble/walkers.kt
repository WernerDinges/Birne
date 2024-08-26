package core.generationEngine.rumble

import core.generationEngine.common.EntityScope
import kotlin.math.pow
import kotlin.random.Random

fun EntityScope.walkers() {
    val dangerLimbs = longestPath.limbs.filter { !it.isVertical && it.canContainDangers }

    val a = 0.25f * (1 + (level.difficulty - 1) / 6f)
    var weights = List(dangerLimbs.size) { i -> (100 * (a.pow(i))).toInt() }.shuffled()

    for(limb in dangerLimbs) {
        val success = Random.nextInt(100) < (weights.lastOrNull() ?: 0)
        if(success)
            level.notPlayableEntities += spawnWalker(level, limb)

        weights = weights.dropLast(1)
    }
}