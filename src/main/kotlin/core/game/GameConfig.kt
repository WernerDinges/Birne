package core.game

import core.entity.player.PlayerInput
import core.generationEngine.GenerationEngine

data class GameConfig(
    val dungeon: Int,
    var difficulty: Int,
    var coins: Int,
    var playerInput: PlayerInput,
    val engine: GenerationEngine
)