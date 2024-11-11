package core.generationEngine.rumble

import core.entity.collectable.Coin
import core.generationEngine.common.CollectableScope
import core.level.TileID.SPIKE
import core.level.TileID.WALL
import kotlin.random.Random

/**
 * Distributes treasures throughout the game level based on the given conditions.
 *
 * This function processes the limbs of the current level to identify potential spots
 * for placing collectables such as coins or containers. The placement is determined
 * randomly, taking into account various in-game elements like walls, spikes, and the
 * longest path. When a valid spot is found, a coin is created and added to the level's
 * collectables.
 *
 * The randomness in treasure placement is controlled by predefined chances:
 * - `coinChance`: The base chance to place a coin.
 * - `containerChance`: The base chance to place a container.
 *
 * @receiver The scope within which collectable items are to be defined. Contains references to
 * the current level configuration, game configuration, the longest path, and the list of limbs.
 */
fun CollectableScope.treasures() {
    val notMainLimbs = limbs.list.filter { edge -> edge !in longestPath.limbs && !edge.isVertical }
    val coinChance = 20
    val containerChance = 10

    for(limb in notMainLimbs) {
        val roll = Random.nextInt(100)
        val containerAdditional = 0
        val coinAdditional = if(limb !in longestPath.limbs) 20 else 0
        if(roll < (containerChance + containerAdditional) + (coinChance + coinAdditional)) {

            var x: Float? = null
            var y: Float? = null
            val tryY = limb.start.second

            chooseX@ for(tryX in (limb.start.first..limb.end.first).shuffled()) {
                if(level.tileSkeleton[tryY][tryX] !in setOf(WALL, SPIKE)
                    && (tryX to tryY) !in setOf(level.startPoint, level.endPoint)) {

                    y = tryY.toFloat()
                    x = tryX.toFloat()
                    break@chooseX

                } else if(level.tileSkeleton[tryY-1][tryX] !in setOf(WALL, SPIKE)
                    && (tryX to tryY-1) !in setOf(level.startPoint, level.endPoint)) {

                    y = (tryY-1).toFloat()
                    x = tryX.toFloat()
                    break@chooseX

                }
            }

            if(x != null && y != null)
                when(roll) {
                    in 0 ..< (containerChance + containerAdditional) -> {
                        // TODO
                    }
                    in (containerChance + containerAdditional) ..< (coinChance + coinAdditional) -> {
                        level.collectables += Coin(x, y, {}).apply { perish = {
                            game.coins += 1
                            level.exclude(this)
                        } }
                    }
                }

        }
    }
}