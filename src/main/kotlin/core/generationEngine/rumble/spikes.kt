package core.generationEngine.rumble

import core.generationEngine.EngineScope
import core.level.TileID.AIR
import core.level.TileID.SPIKE
import core.level.TileID.WALL
import kotlin.math.exp
import kotlin.random.Random

fun EngineScope.spikes() {
    for(limb in limbs.list.filter { !it.isVertical && it.canContainDangers }) {

        val success = Random.nextInt(100) < 10 + 20 * (1 - exp(-.5f * level.difficulty))
        if(limb.canContainDangers && success) {
            for(x in (limb.start.first + 1) ..< limb.end.first) {

                val y = limb.start.second

                val left = level.tileSkeleton[y][x-1] != SPIKE
                val preleft = level.tileSkeleton[y][x-2] != SPIKE
                val notBlockLeft = !(level.tileSkeleton[y][x-1] != WALL && level.tileSkeleton[y-1][x-1] == WALL)
                val notBlockRight = !(level.tileSkeleton[y][x+1] != WALL && level.tileSkeleton[y-1][x+1] == WALL)
                val hole = level.tileSkeleton[limb.start.second + 1][x - 2] != AIR
                val chance = Random.nextInt(14) < level.difficulty + 7

                if((left || preleft) && hole && chance && notBlockLeft && notBlockRight)
                    level.tileSkeleton[y][x] = SPIKE

            }
        }

    }
}