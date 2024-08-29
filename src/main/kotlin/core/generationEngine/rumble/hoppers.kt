package core.generationEngine.rumble

import core.generationEngine.common.EntityScope
import kotlin.math.pow
import kotlin.random.Random

fun EntityScope.hoppers() {
    if(level.difficulty >= 2) {

        val dangerLimbs = longestPath.limbs.filter { !it.isVertical && it.canContainDangers }

        val factor = when(level.difficulty) {
            2 -> .083f
            3 -> .04f
            4 -> .01f
            else -> 0f
        }
        val a = factor * (1 + (level.difficulty - 1) / 6f)
        var weights = List(dangerLimbs.size) { i -> (100 * (a.pow(i))).toInt() }.shuffled()

        for(limb in dangerLimbs) {
            val spawn = Random.nextInt(100) < (weights.lastOrNull() ?: 0)
            if(spawn)
                level.notPlayableEntities += spawnHopper(level, limb)

            weights = weights.dropLast(1)
        }

    }
}