package core.entity.collectable

import Birne.cellSize
import Textures.KEY
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate

data class Key(
    override var x: Float,
    override var y: Float,
    override var perish: () -> Unit
): Collectable {

    override val name = "Key"

    override val hitbox = Size(0.5f, 1f)
    override val hitboxOffset = Offset(0.25f, 0f)

    override var ticks = 0L

    override fun DrawScope.draw(screenWidth: Float, offsetX: Float, offsetY: Float) {
        with(KEY) {
            translate(
                left = offsetX + x * cellSize + 1f,
                top = offsetY + y * cellSize + 1f
            ) {
                draw(Size(cellSize - 1f, cellSize - 1f))
            }
        }
    }

    override fun updateTicks(millis: Long) {  }

    override fun gravitate(millis: Long) {  }
}