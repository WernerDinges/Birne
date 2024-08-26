package core.generationEngine

import core.game.GameConfig
import core.level.LevelConfig

interface GenerationEngine {

    fun launch(levelConfig: LevelConfig, gameConfig: GameConfig)

}