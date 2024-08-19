package screens

import Birne
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.key.onKeyEvent
import core.game.Game
import core.level.Level
import kotlinx.coroutines.delay
import utils.initNewGame

@Composable
fun LevelScreen(state: Game) {
    var initializer by remember { mutableStateOf(true) }
    if(initializer) {
        initNewGame(state)
        initializer = false
    }

    val gameInstance by remember { mutableStateOf(state) }
    val requester = remember { FocusRequester() }

    var trigger by remember { mutableStateOf(0f) }

    Canvas(Modifier
        .fillMaxSize()
        .onKeyEvent { gameInstance.handleKeyEvent(it) }
        .focusRequester(requester)
        .focusable()
    ) {
        with(gameInstance.state as Level) { translate(left = trigger) {

            drawMap()

            drawUI()

        } }
    }

    //Tick update
    LaunchedEffect(Unit) {
        requester.requestFocus()

        val millis = Birne.frameDuration
        while(true)
            with(state.state as Level) {

                if(player.hp <= 0)
                    Birne.state.value = Birne.State.GameOver

                player.apply {
                    updateTicks(millis)
                    gravitate(millis)
                }
                notPlayableEntities.forEach {
                    it.updateTicks(millis)
                    it.gravitate(millis)
                }

                updateEntities(millis)

                trigger = 0.01f
                trigger = 0f
                delay(millis)
            }
    }

}