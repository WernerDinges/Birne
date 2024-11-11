package core.entity

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope

/**
 * An interface representing an entity in a game.
 */
interface Entity {

    /**
     * Represents the name of the entity.
     */
    val name: String

    /**
     * Represents the size of the hitbox for an entity in the game.
     * This variable holds the dimensions that define the collision boundaries.
     * It is used to determine interactions between entities and objects within the game world.
     */
    val hitbox: Size
    /**
     * Represents the offset position of the hitbox relative to its default position.
     * This offset ensures that the hitbox accurately represents the area where collisions
     * with the game object are detected. The offset is typically applied to adjust
     * the hitbox position to align with the visual representation of the object in the game.
     */
    val hitboxOffset: Offset
    /**
     * X position on the game map.
     */
    var x: Float
    /**
     * Y position on the map.
     */
    var y: Float

    /**
     * Draws the entity texture on the screen.
     *
     * @param screenWidth The width of the screen which is used to determine the drawing scale.
     * @param offsetX The horizontal offset applied to the drawing position.
     * @param offsetY The vertical offset applied to the drawing position.
     */
    fun DrawScope.draw(screenWidth: Float, offsetX: Float, offsetY: Float)

    /**
     * Updates the entity ticks based on the provided time in milliseconds.
     * This function is responsible for incrementing the entity's internal
     * tick counter and performing actions that depend on tick progression.
     *
     * @param millis The length of time in milliseconds to update the ticks by.
     */
    fun updateTicks(millis: Long)

    /**
     * Applies gravity to the entity over a specified duration.
     *
     * @param millis The duration in milliseconds over which gravity is applied.
     */
    fun gravitate(millis: Long)

}