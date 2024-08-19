package core.entity

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope

interface Entity {

    val name: String

    val hitbox: Size
    val hitboxOffset: Offset
    var x: Float
    var y: Float

    fun DrawScope.draw(screenWidth: Float, offsetX: Float, offsetY: Float)

    fun updateTicks(millis: Long)

    fun gravitate(millis: Long)

}