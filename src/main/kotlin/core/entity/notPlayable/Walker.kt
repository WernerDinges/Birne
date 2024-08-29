package core.entity.notPlayable

import Birne.cellSize
import Textures.WALKER1
import Textures.WALKER2
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import core.level.TileID.WALL

data class Walker(
    override var x: Float,
    override var y: Float,
    override var isMirrored: Boolean
): NotPlayable {

    override val name = "Walker"

    var speed = 4f

    override val hitbox = Size(0.5f, 0.5f)
    override val hitboxOffset = Offset(0.25f, 0.5f)

    override var vx = 0f
    override var vy = 0f

    override var ticks = 0L

    override fun thinkAndAct(millis: Long, skeleton: Array<Array<Int>>, playerPosition: Offset) {
        val dx = (millis / 1000f) * speed * if(isMirrored) -1f else 1f
        val addHitbox = if(isMirrored) 0f else hitbox.width
        val ix = (x+dx+hitboxOffset.x+addHitbox).toInt()
        val iy = (y+.5f).toInt()
        val safeToMove = skeleton[iy][ix] != WALL && skeleton[iy+1][ix] == WALL && skeleton[iy-1][ix] != WALL

        if(safeToMove)
            x += dx
        else
            isMirrored = !isMirrored
    }

    override fun DrawScope.draw(screenWidth: Float, offsetX: Float, offsetY: Float) {
        with(if(ticks % 250L < 125) WALKER1 else WALKER2) {
            scale(
                scaleX = if(isMirrored) -1f else 1f,
                scaleY = 1f
            ) {
                translate(
                    left = if(!isMirrored) offsetX + x * cellSize + 1f
                            else screenWidth - (offsetX + x * cellSize + 1f) - cellSize,
                    top = offsetY + y * cellSize + 1f
                ) {

                    draw(Size(cellSize - 1f, cellSize - 1f))

                }
            }
        }
    }

    override fun updateTicks(millis: Long) {
        if(ticks + millis < 1000L)
            ticks += millis
        else
            ticks = 0
    }

    override fun gravitate(millis: Long) {  }

}
