package core.generationEngine.common

import core.game.GameConfig
import core.level.LevelConfig

class EntityScope(
    val level: LevelConfig,
    val game: GameConfig,
    longestPath: LimbPath
) {
    val dangerLimbs = longestPath.limbs
        .filter { !it.isVertical && it.canContainDangers }
        .associateWith { true }
        .toMutableMap()
}