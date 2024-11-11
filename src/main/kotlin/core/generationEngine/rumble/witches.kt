package core.generationEngine.rumble

import core.generationEngine.common.EntityScope
import kotlin.math.pow
import kotlin.random.Random

/**
 * Spawns witches at specific locations on the level's playing field based on the level's difficulty.
 *
 * This function calculates a spawn probability for each danger limb on the level. For each danger limb,
 * based on the calculated probability, it decides whether to spawn a witch entity at that limb.
 *
 * - If the level's difficulty is less than 3, no witches are spawned.
 * - The probability for spawning witches increases with increasing level difficulty.
 * - Spawns a maximum of one witch per danger limb.
 * - Once a witch is spawned at a limb, that limb is marked as no longer dangerous.
 *
 * The spawn probability is dynamically calculated based on the danger limb's position in a shuffled list,
 * ensuring some randomness in the witch spawning process.
 *
 * After spawning the witches, the function updates the `notPlayableEntities` of the level configuration
 * to include the newly spawned witches.
 *
 * @receiver EntityScope - The scope containing information about the current level configuration,
 * including the danger limbs and methods for spawning entities.
 */
fun EntityScope.witches() {
    if(level.difficulty >= 3) {

        val a = 0.25f * (1 + (level.difficulty - 1) / 6f)
        var weights = List(dangerLimbs.size) { i -> (100 * (a.pow(i))).toInt() }.shuffled()

        for(limb in dangerLimbs.filter { it.value }.keys) {
            val spawn = Random.nextInt(100) < (weights.lastOrNull() ?: 0)
            if(spawn) {
                level.notPlayableEntities += spawnWitch(level, limb)
                dangerLimbs[limb] = false
            }

            weights = weights.dropLast(1)
        }

    }
}