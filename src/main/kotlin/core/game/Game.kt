package core.game

import androidx.compose.ui.input.key.KeyEvent
import core.level.Level
import core.entity.player.PlayerSkin.*
import core.entity.player.PlayerInput

class Game: Birne.State {

    var levelNumber = 1
    var difficulty = 1
    var coins = 0
    var playerInput = PlayerInput(Classic)

    var state: State = Level(
        levelNumber, difficulty, playerInput,
        { nextLevel() }, { addCoins(it) }, { coins() }
    )

    var handleKeyEvent: (KeyEvent) -> Boolean = { _ -> true }

    fun nextLevel() {
        levelNumber++
        state = Level(
            levelNumber, difficulty, playerInput,
            { nextLevel() }, { addCoins(it) }, { coins() }
        )
    }

    fun addCoins(count: Int) {
        coins += count
    }

    fun coins() = coins

    interface State
}
