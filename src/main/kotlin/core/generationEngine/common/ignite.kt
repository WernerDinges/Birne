package core.generationEngine.common

import core.game.GameConfig
import core.generationEngine.EngineScope
import core.level.LevelConfig

/**
 * Initializes the game engine for a specific level and game configuration. This function
 * sets up the game environment using the provided configurations and applies the given scope
 * to further customize the engine settings and entities.
 *
 * @param level The configuration settings for the current level, including details
 * such as the map size, the start and end points, and the entities within the level.
 * @param game The configuration settings for the game, including the dungeon level,
 * difficulty, coins, and player input configuration.
 * @param scope A lambda with receiver of type `EngineScope` that contains the logic
 * to configure various game components such as limbs, paths, entities, and collectables.
 */
fun ignite(
    level: LevelConfig,
    game: GameConfig,
    scope: EngineScope.() -> Unit
) {
    EngineScope(level, game).apply(scope)
}