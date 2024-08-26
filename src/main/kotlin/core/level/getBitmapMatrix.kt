package core.level

import Textures.DOOR_CLOSED
import Textures.DOOR_OPEN
import Textures.HANG_LAMP
import Textures.VINE_TEXTURE
import Textures.MOSS1_TEXTURE
import Textures.MOSS2_TEXTURE
import Textures.MOSS3_TEXTURE
import Textures.WALL_BOTTOM
import Textures.WALL_BOTTOM_LEFT
import Textures.WALL_BOTTOM_RIGHT
import Textures.WALL_HORIZONTAL
import Textures.WALL_LEFT
import Textures.WALL_NIPPLE_BOTTOM
import Textures.WALL_NIPPLE_LEFT
import Textures.WALL_NIPPLE_RIGHT
import Textures.WALL_NIPPLE_TOP
import Textures.WALL_RIGHT
import Textures.WALL_SOLID
import Textures.WALL_TOP
import Textures.WALL_TOP_LEFT
import Textures.WALL_TOP_RIGHT
import Textures.WALL_VERTICAL
import Textures.LADDER_TEXTURE
import Textures.LEFT_CORNER_MOSS1
import Textures.LEFT_CORNER_MOSS2
import Textures.LEFT_CORNER_WOOD
import Textures.PLATFORM_TEXTURE
import Textures.RIGHT_CORNER_MOSS1
import Textures.RIGHT_CORNER_MOSS2
import Textures.RIGHT_CORNER_WOOD
import Textures.SPIKE_TEXTURE
import androidx.compose.ui.graphics.painter.BitmapPainter
import core.level.TileID.FINISH_CLOSED
import core.level.TileID.FINISH_OPEN
import core.level.TileID.LADDER
import core.level.TileID.LAMP
import core.level.TileID.LEFT_MOSS1
import core.level.TileID.LEFT_MOSS2
import core.level.TileID.LEFT_WOOD
import core.level.TileID.MOSS1
import core.level.TileID.MOSS2
import core.level.TileID.MOSS3
import core.level.TileID.PLATFORM
import core.level.TileID.RIGHT_MOSS1
import core.level.TileID.RIGHT_MOSS2
import core.level.TileID.RIGHT_WOOD
import core.level.TileID.SPIKE
import core.level.TileID.VINE

fun getBitmapMatrix(
    skeleton: Array<Array<Int>>
): Array<Array<BitmapPainter?>> {

    val soup: Array<Array<BitmapPainter?>> =
        Array(skeleton.size) { Array(skeleton[0].size) { null } }

    for(y in soup.indices)
    for(x in soup[0].indices)
        soup[y][x] = when {
            skeleton[y][x] == FINISH_OPEN -> DOOR_OPEN
            skeleton[y][x] == FINISH_CLOSED -> DOOR_CLOSED
            //skeleton[y][x] == KEY -> DOOR_KEY
            //skeleton[y][x] == START -> PLAYER_IDLE
            skeleton[y][x] == LADDER -> LADDER_TEXTURE
            skeleton[y][x] == VINE -> VINE_TEXTURE
            skeleton[y][x] == PLATFORM -> PLATFORM_TEXTURE
            skeleton[y][x] == MOSS1 -> MOSS1_TEXTURE
            skeleton[y][x] == MOSS2 -> MOSS2_TEXTURE
            skeleton[y][x] == MOSS3 -> MOSS3_TEXTURE
            skeleton[y][x] == LEFT_MOSS1 -> LEFT_CORNER_MOSS1
            skeleton[y][x] == RIGHT_MOSS1 -> RIGHT_CORNER_MOSS1
            skeleton[y][x] == LEFT_MOSS2 -> LEFT_CORNER_MOSS2
            skeleton[y][x] == RIGHT_MOSS2 -> RIGHT_CORNER_MOSS2
            skeleton[y][x] == LEFT_WOOD -> LEFT_CORNER_WOOD
            skeleton[y][x] == RIGHT_WOOD -> RIGHT_CORNER_WOOD
            skeleton[y][x] == LAMP -> HANG_LAMP
            skeleton[y][x] == SPIKE -> SPIKE_TEXTURE

            solid(x, y, skeleton) -> WALL_SOLID
            nippleBottom(x, y, skeleton) -> WALL_NIPPLE_BOTTOM
            nippleLeft(x, y, skeleton) -> WALL_NIPPLE_LEFT
            nippleRight(x, y, skeleton) -> WALL_NIPPLE_RIGHT
            nippleTop(x, y, skeleton) -> WALL_NIPPLE_TOP
            bottomLeft(x, y, skeleton) -> WALL_BOTTOM_LEFT
            bottomRight(x, y, skeleton) -> WALL_BOTTOM_RIGHT
            topLeft(x, y, skeleton) -> WALL_TOP_LEFT
            topRight(x, y, skeleton) -> WALL_TOP_RIGHT
            horizontal(x, y, skeleton) -> WALL_HORIZONTAL
            vertical(x, y, skeleton) -> WALL_VERTICAL
            bottom(x, y, skeleton) -> WALL_BOTTOM
            left(x, y, skeleton) -> WALL_LEFT
            right(x, y, skeleton) -> WALL_RIGHT
            top(x, y, skeleton) -> WALL_TOP

            else -> null
        }

    return soup
}



private fun solid(x: Int, y: Int, skeleton: Array<Array<Int>>): Boolean {
    if(skeleton[y][x] != 0) return false

    if(y+1 <= skeleton.lastIndex)
        if(skeleton[y+1][x] == 0) return false

    if(y-1 >= 0)
        if(skeleton[y-1][x] == 0) return false

    if(x+1 <= skeleton[0].lastIndex)
        if(skeleton[y][x+1] == 0) return false

    if(x-1 >= 0)
        if(skeleton[y][x-1] == 0) return false

    return true
}

private fun nippleBottom(x: Int, y: Int, skeleton: Array<Array<Int>>): Boolean {
    if(skeleton[y][x] != 0) return false

    if(y+1 <= skeleton.lastIndex)
        if(skeleton[y+1][x] != 0) return false

    if(y-1 >= 0)
        if(skeleton[y-1][x] == 0) return false

    if(x+1 <= skeleton[0].lastIndex)
        if(skeleton[y][x+1] == 0) return false

    if(x-1 >= 0)
        if(skeleton[y][x-1] == 0) return false

    return true
}

private fun nippleLeft(x: Int, y: Int, skeleton: Array<Array<Int>>): Boolean {
    if(skeleton[y][x] != 0) return false

    if(y+1 <= skeleton.lastIndex)
        if(skeleton[y+1][x] == 0) return false

    if(y-1 >= 0)
        if(skeleton[y-1][x] == 0) return false

    if(x+1 <= skeleton[0].lastIndex)
        if(skeleton[y][x+1] == 0) return false

    if(x-1 >= 0)
        if(skeleton[y][x-1] != 0) return false

    return true
}

private fun nippleRight(x: Int, y: Int, skeleton: Array<Array<Int>>): Boolean {
    if(skeleton[y][x] != 0) return false

    if(y+1 <= skeleton.lastIndex)
        if(skeleton[y+1][x] == 0) return false

    if(y-1 >= 0)
        if(skeleton[y-1][x] == 0) return false

    if(x+1 <= skeleton[0].lastIndex)
        if(skeleton[y][x+1] != 0) return false

    if(x-1 >= 0)
        if(skeleton[y][x-1] == 0) return false

    return true
}

private fun nippleTop(x: Int, y: Int, skeleton: Array<Array<Int>>): Boolean {
    if(skeleton[y][x] != 0) return false

    if(y+1 <= skeleton.lastIndex)
        if(skeleton[y+1][x] == 0) return false

    if(y-1 >= 0)
        if(skeleton[y-1][x] != 0) return false

    if(x+1 <= skeleton[0].lastIndex)
        if(skeleton[y][x+1] == 0) return false

    if(x-1 >= 0)
        if(skeleton[y][x-1] == 0) return false

    return true
}

private fun bottomLeft(x: Int, y: Int, skeleton: Array<Array<Int>>): Boolean {
    if(skeleton[y][x] != 0) return false

    if(x+1 <= skeleton[0].lastIndex) {
        if(skeleton[y][x+1] == 0) return false
    } else
        return false

    if(y-1 >= 0) {
        if(skeleton[y-1][x] == 0) return false
    } else
        return false

    return true
}

private fun bottomRight(x: Int, y: Int, skeleton: Array<Array<Int>>): Boolean {
    if(skeleton[y][x] != 0) return false

    if(x-1 >= 0) {
        if(skeleton[y][x-1] == 0) return false
    } else
        return false

    if(y-1 >= 0) {
        if(skeleton[y-1][x] == 0) return false
    } else
        return false

    return true
}

private fun topLeft(x: Int, y: Int, skeleton: Array<Array<Int>>): Boolean {
    if(skeleton[y][x] != 0) return false

    if(x+1 <= skeleton[0].lastIndex) {
        if(skeleton[y][x+1] == 0) return false
    }

    if(y+1 <= skeleton.lastIndex) {
        if(skeleton[y+1][x] == 0) return false
    } else
        return false

    return true
}

private fun topRight(x: Int, y: Int, skeleton: Array<Array<Int>>): Boolean {
    if(skeleton[y][x] != 0) return false

    if(x-1 >= 0) {
        if(skeleton[y][x-1] == 0) return false
    } else
        return false

    if(y+1 <= skeleton.lastIndex) {
        if(skeleton[y+1][x] == 0) return false
    } else
        return false

    return true
}

private fun horizontal(x: Int, y: Int, skeleton: Array<Array<Int>>): Boolean {
    if(skeleton[y][x] != 0) return false

    if(y-1 >= 0 && y+1 <= skeleton.lastIndex) {
        if(skeleton[y-1][x] == 0 || skeleton[y+1][x] == 0) return false
    } else
        return false

    return true
}

private fun vertical(x: Int, y: Int, skeleton: Array<Array<Int>>): Boolean {
    if(skeleton[y][x] != 0) return false

    if(x-1 >= 0 && x+1 <= skeleton[0].lastIndex) {
        if(skeleton[y][x-1] == 0 || skeleton[y][x+1] == 0) return false
    } else
        return false

    return true
}

private fun bottom(x: Int, y: Int, skeleton: Array<Array<Int>>): Boolean {
    if(skeleton[y][x] != 0) return false

    if(y-1 >= 0) {
        if(skeleton[y-1][x] == 0) return false
    } else
        return false

    return true
}

private fun left(x: Int, y: Int, skeleton: Array<Array<Int>>): Boolean {
    if(skeleton[y][x] != 0) return false

    if(x+1 <= skeleton[0].lastIndex) {
        if(skeleton[y][x+1] == 0) return false
    } else
        return false

    return true
}

private fun right(x: Int, y: Int, skeleton: Array<Array<Int>>): Boolean {
    if(skeleton[y][x] != 0) return false

    if(x-1 >= 0) {
        if(skeleton[y][x-1] == 0) return false
    } else
        return false

    return true
}

private fun top(x: Int, y: Int, skeleton: Array<Array<Int>>): Boolean {
    if(skeleton[y][x] != 0) return false

    if(y+1 <= skeleton.lastIndex) {
        if(skeleton[y+1][x] == 0) return false
    } else
        return false

    return true
}