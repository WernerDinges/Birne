package core.game

import androidx.compose.ui.input.key.KeyEvent
import core.entity.collectable.Collectable
import core.entity.notPlayable.NotPlayable
import core.level.Level
import core.level.LevelConfig
import core.level.getLevelSize
import core.level.TileID.WALL
import kotlin.random.Random

class Game(val config: GameConfig) {

    val dungeon = 0
    var levelNumber = 1

    var level: Level? = null

    var handleKeyEvent: (KeyEvent) -> Boolean = { _ -> true }

    fun newLevel(): Level {
        val levelConfig = blankLevelConfig(levelNumber, config.difficulty)
        return Level(levelConfig, config, config.playerInput)
    }

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
