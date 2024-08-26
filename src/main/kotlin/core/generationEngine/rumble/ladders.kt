package core.generationEngine.rumble

import core.generationEngine.EngineScope
import core.level.TileID.AIR
import core.level.TileID.LADDER
import core.level.TileID.PLATFORM
import core.level.TileID.VINE
import core.level.TileID.WALL
import kotlin.random.Random

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