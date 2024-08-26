package core.generationEngine.common

import core.game.GameConfig
import core.generationEngine.EngineScope
import core.level.LevelConfig

fun ignite(
    level: LevelConfig,
    game: GameConfig,
    scope: EngineScope.() -> Unit
) {
    EngineScope(level, game).apply(scope)
}