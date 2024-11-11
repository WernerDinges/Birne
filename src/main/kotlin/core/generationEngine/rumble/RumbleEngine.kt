package core.generationEngine.rumble

import core.game.GameConfig
import core.generationEngine.GenerationEngine
import core.generationEngine.common.ignite
import core.level.LevelConfig

/**
 * RumbleEngine is a basic engine to generate fuzzy and weird levels.
 */
object RumbleEngine: GenerationEngine {
    override fun launch(levelConfig: LevelConfig, gameConfig: GameConfig) = ignite(levelConfig, gameConfig) {

        limbs { generateLimbs() }

        drillHoles()

        longestPath { dfsPath() }

        entities {
            walkers()
            hoppers()
            witches()
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