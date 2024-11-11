package core.generationEngine.rumble

import core.generationEngine.EngineScope
import core.level.TileID.AIR
import core.level.TileID.WALL
import kotlin.random.Random

/**
 * Creates hollow spaces in vertical limbs and marks certain limbs to contain dangers based on specific conditions.
 *
 * The method first filters the vertical limbs and groups them by their starting Y-coordinate. It then
 * iterates through these grouped limbs to possibly create holes in the tile skeleton between close
 * adjacent limbs. If a hole is drilled, it updates the level's tile skeleton to replace the tiles
 * with air and adds these positions to the list of decoratables.
 *
 * After creating the holes, the method updates the `canContainDangers` property for each limb, based
 * on the width of the tunnel, presence of a floor, proximity to the player, and size.
 */
fun EngineScope.drillHoles() {
    val groupedVerticals = limbs.list
        .filter { it.isVertical }
        .groupBy { it.start.second }

    for((_, floor) in groupedVerticals)
        for((left, right) in floor.windowed(size = 2, step = 1))
            if(right.start.first-left.start.first < 4 && Random.nextBoolean()) {

                // Drilling a hole
                for(x in left.start.first..right.start.first)
                for(y in left.start.second..left.end.second) {
                    level.tileSkeleton[y][x] = AIR
                    // Adding to decoratables
                    decoratables.hollow += Pair(x, y)
                }

            }

    for(limb in limbs.list) limb.apply {
        canContainDangers = (
            // The tunnel is wide enough and has floor
            level.tileSkeleton[start.second-1][middleX()] != WALL &&
            level.tileSkeleton[start.second+1][middleX()] == WALL &&
            // No player near
            !(level.startPoint.first in start.first..end.first
                && level.startPoint.second == start.second) &&
            // Big enough
            end.first-start.first > 2
        )
    }
}