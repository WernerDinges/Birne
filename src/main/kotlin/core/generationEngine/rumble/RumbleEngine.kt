package core.generationEngine.rumble

import core.game.GameConfig
import core.generationEngine.GenerationEngine
import core.generationEngine.common.ignite
import core.level.LevelConfig

object RumbleEngine: GenerationEngine {
    override fun launch(levelConfig: LevelConfig, gameConfig: GameConfig) = ignite(levelConfig, gameConfig) {

        limbs { generateLimbs() }

        drillHoles()

        longestPath { dfsPath() }

        entities {
            walkers()
            hoppers()
        }

        platforms()

        ladders()

        decorations()

        spikes()

        collectables {
            treasures()
        }

        drawStartAndFinish()

    }
}