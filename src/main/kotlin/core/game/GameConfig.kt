package core.game

import core.entity.player.PlayerInput
import core.generationEngine.GenerationEngine

/**
 * Represents the configuration settings for a game, including the dungeon level,
 * difficulty, coin count, player input configuration, and the generation engine.
 *
 * @property dungeon The dungeon level for the game.
 * @property difficulty The difficulty level of the game, which may affect various game elements.
 * @property coins The number of coins available to the player.
 * @property playerInput The configuration of player input, including the player's skin.
 * @property engine The game generation engine responsible for creating and launching game levels based on the configurations.
 */
data class GameConfig(
    val dungeon: Int,
    var difficulty: Int,
    var coins: Int,
    var playerInput: PlayerInput,
    val engine: GenerationEngine
)