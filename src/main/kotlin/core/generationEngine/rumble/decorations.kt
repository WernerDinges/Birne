package core.generationEngine.rumble

import core.generationEngine.EngineScope
import core.level.TileID.AIR
import core.level.TileID.LAMP
import core.level.TileID.LEFT_MOSS1
import core.level.TileID.LEFT_MOSS2
import core.level.TileID.LEFT_WOOD
import core.level.TileID.MOSS1
import core.level.TileID.MOSS2
import core.level.TileID.MOSS3
import core.level.TileID.RIGHT_MOSS1
import core.level.TileID.RIGHT_MOSS2
import core.level.TileID.RIGHT_WOOD
import core.level.TileID.WALL
import kotlin.random.Random

fun EngineScope.decorations() {

    for((x, y) in decoratables.hollow) if(Random.nextBoolean() && level.tileSkeleton[y][x] == AIR)
        when {
            // Left corner
            level.tileSkeleton[y-1][x] == WALL && level.tileSkeleton[y][x-1] == WALL -> {
                level.tileSkeleton[y][x] = setOf(LEFT_MOSS1, LEFT_MOSS2, LEFT_WOOD).random()
            }

            // Right corner
            level.tileSkeleton[y-1][x] == WALL && level.tileSkeleton[y][x+1] == WALL -> {
                level.tileSkeleton[y][x] = setOf(RIGHT_MOSS1, RIGHT_MOSS2, RIGHT_WOOD).random()
            }

            // Ceiling
            level.tileSkeleton[y-1][x] == WALL -> {
                level.tileSkeleton[y][x] = setOf(MOSS1, MOSS2, MOSS3, LAMP).random()
            }
        }

}