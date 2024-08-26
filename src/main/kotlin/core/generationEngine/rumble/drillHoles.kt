package core.generationEngine.rumble

import core.generationEngine.EngineScope
import core.level.TileID.AIR
import core.level.TileID.WALL
import kotlin.random.Random

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