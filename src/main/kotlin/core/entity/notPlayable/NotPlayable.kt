package core.entity.notPlayable

import core.entity.Entity
import core.entity.player.Player
import core.level.LevelConfig

/**
 * Represents entities that are not controlled by the player but still have
 * movement and environmental interaction within the game.
 */
interface NotPlayable: Entity {

    /**
     * Represents the horizontal velocity of the entity in a game.
     * The value of this property indicates the speed and direction of movement along the X-axis.
     */
    var vx: Float
    /**
     * Represents the vertical (Y-axis) velocity of the entity.
     * This variable indicates the speed and direction of the entity's movement
     * along the Y-axis. Positive values typically indicate downward movement,
     * while negative values indicate upward movement.
     */
    var vy: Float

    /**
     * Indicates whether the entity has collision or not.
     */
    val collision: Boolean

    /**
     * Represents the total number of ticks passed within the period.
     * This variable tracks the time progression for the `NotPlayable` entity
     * by incrementing over intervals, allowing actions to be performed based on tick counts.
     */
    var ticks: Long
    /**
     * Indicates whether the entity's texture is mirrored horizontally.
     */
    var isMirrored: Boolean

    /**
     * Executes the thinking algorithm and performs an action based on the current game state.
     *
     * @param millis The time in milliseconds allotted for the thinking process.
     * @param skeleton The game board represented as a 2D array of integers.
     * @param level The configuration settings for the current game level.
     * @param player The player who is executing the action.
     */
    fun thinkAndAct(millis: Long, skeleton: Array<Array<Int>>, level: LevelConfig, player: Player)

}