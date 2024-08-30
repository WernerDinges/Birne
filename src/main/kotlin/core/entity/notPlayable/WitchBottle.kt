package core.entity.notPlayable

import Birne.cellSize
import Textures.WITCH_BOTTLE
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import core.entity.player.Player
import core.level.LevelConfig
import core.level.TileID.WALL
import kotlin.math.pow
import kotlin.math.sqrt

data class WitchBottle(
    override var x: Float,
    override var y: Float,
    override var vx: Float,
    override var vy: Float,
    override var isMirrored: Boolean
): NotPlayable {

    override val name = "Witch poison bottle"

    override val collision = false

    val waitTime = 2000L

    override val hitbox = Size(0.5f, 0.5f)
    override val hitboxOffset = Offset(0.25f, 0.25f)

    private val bounceStrength = -vy

    override var ticks = 0L

    override fun thinkAndAct(millis: Long, skeleton: Array<Array<Int>>, level: LevelConfig, player: Player) {
        val r = sqrt(
            (player.x - x).pow(2) +
            (player.y - y).pow(2)
        )
        if(r < .3f) {
            player.hurt(level)
            level.exclude(this)
        }

        if(ticks > waitTime) {
            level.exclude(this)
        }

        val dx = (millis / 1000f) * vx
        val dy = (millis / 1000f) * vy
        val addX = if(vx < 0) 0f else hitbox.width
        val addY = if(vy < 0) 0f else hitbox.height
        val ix = (x+hitboxOffset.x+addX).toInt()
        val iy = (y+hitboxOffset.y+addY).toInt()
        val ixx = (x+dx+hitboxOffset.x+addX).toInt()
        val iyy = (y+dy+hitboxOffset.y+addY).toInt()

        val movableX = skeleton[iy][ixx] != WALL
        if(movableX) x += dx else vx *= -1

        val movableY = skeleton[iyy][ix] != WALL
        if(movableY) y += dy else vy *= -1
    }

    override fun DrawScope.draw(screenWidth: Float, offsetX: Float, offsetY: Float) {
        with(WITCH_BOTTLE) {
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
        ticks += millis
    }

    override fun gravitate(millis: Long) {
        val dvy = bounceStrength * (millis / 1000f) * 4f

        if(vy + dvy <= bounceStrength)
            vy += dvy/2f
        else
            vy = bounceStrength
    }

}
