package core.generationEngine

import core.game.GameConfig
import core.level.LevelConfig

/**
 * Represents an engine responsible for generating and launching game levels.
 */
interface GenerationEngine {

    /**
     * Launches the game with the specified level and game configurations.
     *
     * @param levelConfig The configuration settings for the level, including details such as the map size,
     * start and end points, tile skeleton, and entities within the level.
     * @param gameConfig The configuration settings for the game, including the dungeon level, difficulty,
     * coin count, player input configuration, and the generation engine.
     */
    fun launch(levelConfig: LevelConfig, gameConfig: GameConfig)

}