package core.generationEngine.common

import core.game.GameConfig
import core.level.LevelConfig

class EntityScope(
    val level: LevelConfig,
    val game: GameConfig,
    val longestPath: LimbPath
)