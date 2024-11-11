package core.game

import androidx.compose.ui.input.key.KeyEvent
import core.entity.collectable.Collectable
import core.entity.notPlayable.NotPlayable
import core.level.Level
import core.level.LevelConfig
import core.level.getLevelSize
import core.level.TileID.WALL
import kotlin.random.Random

/**
 * Represents a game with a specific configuration.
 * @property config Configuration parameters for the game.
 */
class Game(val config: GameConfig) {

    val dungeon = 0

    var levelNumber = 1

    var level: Level? = null

    /**
     * A higher-order function that takes a KeyEvent as an argument and returns a Boolean.
     * By default, it is set to always return true.
     *
     * This variable can be assigned a custom lambda function to handle key events according to specific requirements.
     *
     * @param KeyEvent The key event that is to be processed.
     * @return Boolean The result of the handling function, indicates whether the key event was handled or not.
     */
    var handleKeyEvent: (KeyEvent) -> Boolean = { _ -> true }

    /**
     * Creates a new game level based on the current level number and game configuration.
     *
     * @return a new `Level` object configured with the current level number and game difficulty.
     */
    fun newLevel(): Level {
        val levelConfig = blankLevelConfig(levelNumber, config.difficulty)
        return Level(levelConfig, config, config.playerInput)
    }

    /**
     * Generates a blank level configuration based on the provided level number and difficulty.
     *
     * @param number The identifier or number of the level.
     * @param difficulty The difficulty setting for the level, influencing its layout and challenges.
     * @return A new instance of `LevelConfig` populated with default placeholders and random elements.
     */
    private fun blankLevelConfig(number: Int, difficulty: Int): LevelConfig {
        val mapSize = getLevelSize(difficulty)
        val tileSkeleton = Array(mapSize.second) { Array(mapSize.first) { WALL } } // Placeholder
        val startPoint = 0 to 0 // Placeholder
        val endPoint = 0 to 0 // Placeholder
        val notPlayableEntities = listOf<NotPlayable>() // Placeholder
        val collectables = listOf<Collectable>() // Placeholder
        val doorClosed = Random.nextBoolean()

        return LevelConfig(
            number, difficulty, mapSize,
            startPoint, endPoint,
            tileSkeleton,
            notPlayableEntities, collectables,
            doorClosed
        )
    }

}