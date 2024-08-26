package core.generationEngine.rumble

import core.generationEngine.EngineScope
import core.level.TileID.AIR
import core.level.TileID.FINISH_CLOSED
import core.level.TileID.FINISH_OPEN
import core.level.TileID.PLATFORM
import core.level.TileID.START
import core.level.TileID.WALL
import kotlin.random.Random

fun EngineScope.platforms() {
    val limbs = longestPath.limbs.filter { !it.isVertical }

    for(limb in limbs) {
        val y = limb.end.second

        for(x in limb.start.first..limb.end.first) {
            val noFloor = level.tileSkeleton[y+1][x] != WALL
            val spawnOrFinish = (x to y) in setOf(level.startPoint, level.endPoint)
            val success = Random.nextBoolean()
            val holeLeft = level.tileSkeleton[y+1][x-1] == AIR
            val holeRight = level.tileSkeleton[y+1][x+1] == AIR

            if(noFloor && (spawnOrFinish || success || (holeLeft && holeRight)))
                level.tileSkeleton[y+1][x] = PLATFORM
        }
    }

    if(level.tileSkeleton[level.startPoint.second+1][level.startPoint.first] != WALL)
        level.tileSkeleton[level.startPoint.second+1][level.startPoint.first] = PLATFORM

    if(level.tileSkeleton[level.endPoint.second+1][level.endPoint.first] != WALL)
        level.tileSkeleton[level.endPoint.second+1][level.endPoint.first] = PLATFORM
}