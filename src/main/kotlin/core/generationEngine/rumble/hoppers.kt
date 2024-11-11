package core.generationEngine.rumble

import core.generationEngine.common.EntityScope
import kotlin.math.pow
import kotlin.random.Random

/**
 * Spawns 'Hopper' entities on the dangerous limbs of the level based on the difficulty level. The spawning algorithm
 * is influenced by the difficulty, where higher difficulty levels increase the likelihood of spawning more Hoppers.
 *
 * This function operates within an `EntityScope`, which maintains information about hazardous limbs and the
 * current game level configuration. Hoppers are only spawned on limbs marked as dangerous.
 *
 * Steps performed by this method:
 * 1. Calculates a probability factor `a` based on the level's difficulty.
 * 2. Generates a list of weights for each dangerous limb, which determine the likelihood of spawning a Hopper.
 * 3. Iterates over each dangerous limb:
 *    - Randomly decides whether to spawn a Hopper based on the calculated weights.
 *    - If a Hopper is spawned, it is added to the level's non-playable entities and the limb is marked as safe.
 *    - Reduces the weight list appropriately for the next iteration.
 */
fun EntityScope.hoppers() {
    if(level.difficulty >= 2) {

        val a = 0.25f * (1 + (level.difficulty - 1) / 6f)
        var weights = List(dangerLimbs.size) { i -> (100 * (a.pow(i))).toInt() }.shuffled()

        for(limb in dangerLimbs.filter { it.value }.keys) {
            val spawn = Random.nextInt(100) < (weights.lastOrNull() ?: 0)
            if(spawn) {
                level.notPlayableEntities += spawnHopper(level, limb)
                dangerLimbs[limb] = false
            }

            weights = weights.dropLast(1)
        }

    }
}