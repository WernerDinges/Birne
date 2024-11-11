package core.generationEngine.rumble

import core.generationEngine.common.EntityScope
import kotlin.math.pow
import kotlin.random.Random

/**
 * Spawns Walkers based on the danger limbs within the entity scope.
 *
 * This method calculates a spawn probability based on the level difficulty and
 * the danger limbs associated with the longest path. For each danger limb that is
 * still marked as dangerous, it determines whether a walker should be spawned. If
 * a walker is spawned, it is added to the level's non-playable entities, and the
 * danger limb is marked as safe.
 */
fun EntityScope.walkers() {
    val a = 0.25f * (1 + (level.difficulty - 1) / 6f)
    var weights = List(dangerLimbs.size) { i -> (100 * (a.pow(i))).toInt() }.shuffled()

    for(limb in dangerLimbs.filter { it.value }.keys) {
        val spawn = Random.nextInt(100) < (weights.lastOrNull() ?: 0)
        if(spawn) {
            level.notPlayableEntities += spawnWalker(level, limb)
            dangerLimbs[limb] = false
        }

        weights = weights.dropLast(1)
    }
}