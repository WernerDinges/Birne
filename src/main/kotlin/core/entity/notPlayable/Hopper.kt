package core.entity.notPlayable

import Birne.cellSize
import Textures.HOPPER
import Textures.HOPPER_JUMP
import Textures.HOPPER_LOAD1
import Textures.HOPPER_LOAD2
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import core.level.TileID.WALL
import kotlin.math.pow


data class Hopper(
    override var x: Float,
    override var y: Float,
    override var isMirrored: Boolean
): NotPlayable {

    override val name = "Hopper"

    var waitTime = 500L

    override val hitbox = Size(0.5f, 0.5f)
    override val hitboxOffset = Offset(0.25f, 0.5f)

    override var vx = 0f
    override var vy = 0f

    var speed = 8f
    val jumpStrength = 10f
    val vision = 2f

    override var ticks = 0L

    private var state: State = State.Idle
    sealed interface State {
        data object Idle : State
        data object Load : State
        data object Jump : State
    }

    override fun thinkAndAct(millis: Long, skeleton: Array<Array<Int>>, playerPosition: Offset) {
        if(state != State.Jump) {
            val aware = ((playerPosition.x - (x+.5f)) / (3f * vision)).pow(2) + ((playerPosition.y - (y+.5f)) / vision).pow(2) <= 1f
            if(aware)
                state = State.Load
        }
        if(ticks >= 2f*waitTime) {
            state = State.Jump
            vy = -jumpStrength
            vx = (speed) * if(playerPosition.x > x) 1f else -1f
            isMirrored = !(playerPosition.x > x)
        }
        if(state == State.Jump) {
            val dx = (millis / 1000f) * vx
            val dy = (millis / 1000f) * vy
            val addX = if(isMirrored) 0f else hitbox.width
            val addY = if(vy < 0) 0f else hitbox.height
            val ix = (x+hitboxOffset.x+addX).toInt()
            val iy = (y+hitboxOffset.y+addY).toInt()
            val ixx = (x+dx+hitboxOffset.x+addX).toInt()
            val iyy = (y+dy+hitboxOffset.y+addY).toInt()

            val movableX = skeleton[iy][ixx] != WALL
            if(movableX) x += dx else vx = 0f

            val movableY = skeleton[iyy][ix] != WALL
            if(movableY) y += dy else state = State.Idle
        }
    }

    override fun DrawScope.draw(screenWidth: Float, offsetX: Float, offsetY: Float) {
        with(when(state) {
            State.Idle -> HOPPER
            State.Load -> if(ticks < waitTime) HOPPER_LOAD1 else HOPPER_LOAD2
            State.Jump -> HOPPER_JUMP
        }) {
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
        if(state == State.Load)
            ticks += millis
        else
            ticks = 0
    }

    override fun gravitate(millis: Long) {
        if(state == State.Jump) {

            val dvy = jumpStrength * (millis / 1000f) * 4f

            if(vy + dvy <= jumpStrength)
                vy += dvy
            else
                vy = jumpStrength

        }
    }

}
