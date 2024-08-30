package core.entity.notPlayable

import Birne.cellSize
import Textures.WITCH
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import core.entity.player.Player
import core.generationEngine.common.EntityScope
import core.level.LevelConfig
import kotlin.math.pow

data class Witch(
    val scope: EntityScope,
    override var x: Float,
    override var y: Float,
    override var isMirrored: Boolean
): NotPlayable {

    override val name = "Witch"

    override val collision = false

    val waitTime = 1000L

    override val hitbox = Size(0.5f, 0.5f)
    override val hitboxOffset = Offset(0.25f, 0.5f)

    override var vx = 0f
    override var vy = 0f

    val vBottle = 5f
    val bounceStrength = 5f

    val vision = 2f

    override var ticks = 0L

    private var state: State = State.Idle
    sealed interface State {
        data object Idle : State
        data object Aware : State
    }

    override fun thinkAndAct(millis: Long, skeleton: Array<Array<Int>>, level: LevelConfig, player: Player) {
        val aware = ((player.x - (x+.5f)) / (3f * vision)).pow(2) + ((player.y - (y+.5f)) / vision).pow(2) <= 1f
        state = if(aware) State.Aware else State.Idle

        if(state == State.Aware)
            isMirrored = !(player.x > x)

        if(ticks > waitTime) {
            ticks = 0
            scope.level.notPlayableEntities += WitchBottle(
                x = x, y = y,
                vx = if(isMirrored) -vBottle else vBottle,
                vy = -bounceStrength,
                isMirrored = isMirrored
            )
        }
    }

    override fun DrawScope.draw(screenWidth: Float, offsetX: Float, offsetY: Float) {
        with(WITCH) {
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
        if(state == State.Aware)
            ticks += millis
        else
            ticks = 0
    }

    override fun gravitate(millis: Long) {  }

}
