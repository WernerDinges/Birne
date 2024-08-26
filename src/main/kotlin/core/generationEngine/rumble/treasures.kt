package core.generationEngine.rumble

import core.entity.collectable.Coin
import core.generationEngine.common.CollectableScope
import core.level.TileID.SPIKE
import core.level.TileID.WALL
import kotlin.random.Random

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