package screens

import Birne
import Birne.cellSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.key.*
import core.game.Game
import kotlinx.coroutines.delay
import utils.drawText

@Composable
fun MenuScreen() {
    val requester = remember { FocusRequester() }
    val title = "BIRNE BY DANIEL DINGES"
    val play = "[SPACE] TO PLAY"

    val (screenSize, resizeScreen) = remember { mutableStateOf(Size.Zero) }

    Canvas(
        Modifier
            .fillMaxSize()
            .onKeyEvent { event ->
                if(event.key == Key.Spacebar && event.type == KeyEventType.KeyDown)
                    Birne.state.value = Game()

                true
            }
            .focusRequester(requester)
            .focusable()
    ) {
        resizeScreen(size)
        cellSize = screenSize.height/16f

        drawText(
            text = title,
            left = { i: Int -> (size.width - title.length*cellSize/2f)/2f + i*cellSize/2f },
            top = { 0.5f * cellSize }
        )

        drawText(
            text = play,
            left = { i: Int -> (size.width - play.length*cellSize/2f)/2f + i*cellSize/2f },
            top = { size.height/2f /*- 1.5f*cellSize*/ }
        )

    }

    LaunchedEffect(Unit) {
        requester.requestFocus()

        while(true) {
            cellSize = screenSize.height/16f

            delay(10L)
        }
    }
}