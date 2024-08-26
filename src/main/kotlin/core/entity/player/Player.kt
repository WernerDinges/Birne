package core.entity.player

import Birne.cellSize
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import core.entity.Entity
import core.game.Game

data class Player(
    override var x: Float,
    override var y: Float,
    val playerInput: PlayerInput,
): Entity {

    override val name = "Player"

    val maxHp = 3
    var hp = maxHp
    var speed = 8f
    var jumpStrength = 10f
    val doubleJumpCooldown = 3000L
    var doubleJumpLeft = 0L

    private val skin = playerInput.skin
    override val hitbox = Size(0.5f, 0.75f)
    override val hitboxOffset = Offset(0.25f, 0.25f)

    var vx = 0f
    var vy = 0f

    var ticks = 0L
    var state = PlayerState.IDLE
    var isMirrored = false
    var isLadder = false

    override fun DrawScope.draw(screenWidth: Float, offsetX: Float, offsetY: Float) {

        val texture = when(state) {
            PlayerState.IDLE -> skin.idle
            PlayerState.MOVE -> if(ticks % 250L < 125) skin.move1 else skin.move2
            PlayerState.JUMP -> skin.jump

            else -> skin.idle
        }

        with(texture) {
            scale(
                scaleX = if(isMirrored) -1f else 1f,
                scaleY = 1f
            ) {
                translate(
                    left = if(!isMirrored) offsetX + x * cellSize + 1f
                        else screenWidth - (offsetX + x * cellSize + 1f) - cellSize,
                    top = offsetY + y * cellSize + 1.5f
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

        doubleJumpLeft = (doubleJumpLeft - millis).coerceAtLeast(0L)
    }

    override fun gravitate(millis: Long) {
        if(state != PlayerState.CLIMB) {

            val dvy = jumpStrength * (millis / 1000f) * 4f

            if (vy + dvy <= jumpStrength)
                vy += dvy
            else
                vy = jumpStrength

        }
    }

}
