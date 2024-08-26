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
import kotlinx.coroutines.delay
import utils.each

@Composable
fun LevelScreen(state: Birne.State.Play) {
    val requester = remember { FocusRequester() }

    var trigger by remember { mutableStateOf(0f) }

    Canvas(Modifier
        .fillMaxSize()
        .onKeyEvent { state.instance.handleKeyEvent(it) }
        .focusRequester(requester)
        .focusable()
    ) {
        with(state.instance.level!!) { translate(left = trigger) {

            drawMap()

            drawUI()

        } }
    }

    //Tick update
    LaunchedEffect(Unit) {
        requester.requestFocus()

        val millis = Birne.FRAME_DURATION
        while(true)
            with(state.instance.level!!) {

                if(player.hp <= 0)
                    Birne.gameOver(
                        state.instance.dungeon,
                        state.instance.config.coins,
                        state.instance.levelNumber
                    )

                player.apply {
                    updateTicks(millis)
                    gravitate(millis)
                }
                config.notPlayableEntities.each {
                    updateTicks(millis)
                    gravitate(millis)
                }

                updateEntities(millis)

                trigger = 0.01f
                trigger = 0f
                delay(millis)
            }
    }

}