package core.generationEngine.rumble

import core.generationEngine.EngineScope
import core.level.TileID.AIR
import core.level.TileID.SPIKE
import core.level.TileID.WALL
import kotlin.math.exp
import kotlin.random.Random

/**
 * Places spikes on eligible horizontal limbs within the level configuration.
 *
 * This function iterates over all limbs in the `limbs` list and checks if they are
 * horizontal (`!limb.isVertical`) and can contain dangers (`limb.canContainDangers`).
 * For each eligible limb, a random chance, influenced by the level's difficulty,
 * determines if spikes should be placed.
 *
 * If the probability check is successful, the function evaluates each tile
 * position within the limb. It ensures the placement of spikes under various
 * conditions, such as adjacency to walls, the absence of predefined blocks, and
 * proximity to existing spikes. If all conditions are met, a spike is placed
 * at the determined position.
 */
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