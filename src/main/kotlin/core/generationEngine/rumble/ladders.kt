package core.generationEngine.rumble

import core.generationEngine.EngineScope
import core.level.TileID.AIR
import core.level.TileID.LADDER
import core.level.TileID.PLATFORM
import core.level.TileID.VINE
import core.level.TileID.WALL
import kotlin.random.Random

/**
 * Adds ladders or vines to the vertical limbs in the level configuration.
 *
 * This method processes the longest path's vertical limbs, grouping them by their horizontal positions.
 * Each vertical limb group is then sorted based on their vertical start positions. Depending on a random
 * boolean, a vertical limb is either filled with ladder or vine tiles.
 *
 * The method performs the following actions:
 * - Groups and sorts the vertical limbs.
 * - Fills the gaps between the vertical limbs with ladder or vine tiles.
 * - If a vine is chosen, it also grows upwards until it meets a ceiling or wall.
 */
fun EngineScope.ladders() {
    val verticals = longestPath.limbs
        .filter { it.isVertical }
        .groupBy { it.end.first }
        .toMutableMap()
    verticals.forEach { (group, list) ->
        verticals[group] = list.sortedBy { it.start.second }
    }

    for((x, limbs) in verticals) {

        val ladder = if(Random.nextBoolean()) LADDER else VINE

        for(y in (limbs.first().start.second+1)..limbs.last().end.second)
            if(level.tileSkeleton[y][x] == AIR)
                level.tileSkeleton[y][x] = ladder

        // Vine can grow up to the ceiling
        if(ladder == VINE && level.tileSkeleton[limbs.first().start.second+1][x] != PLATFORM)
            for(y in limbs.first().start.second.downTo(1))
                when(level.tileSkeleton[y][x]) {
                    WALL, PLATFORM -> break
                    AIR -> level.tileSkeleton[y][x] = VINE
                    else -> continue
                }
    }
}