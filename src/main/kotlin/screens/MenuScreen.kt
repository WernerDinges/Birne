package screens

import Birne
import Birne.cellSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.key.*
import kotlinx.coroutines.delay
import utils.drawText

@Composable
fun MenuScreen() {
    val requester = remember { FocusRequester() }

    val gameTitle by remember { mutableStateOf("BIRNE BY DANIEL DINGES") }
    val coinsCount by remember { mutableStateOf("COINS: ${Birne.gameData.coins}") }
    val hint by remember { mutableStateOf("[SPACE] OK, [W] UP, [S] DOWN") }

    val options by remember { mutableStateOf(listOf("DUNGEONS", "SHOP", "OPTIONS", "STATS")) }
    var selected by remember { mutableStateOf(0) }

    val (screenSize, resizeScreen) = remember { mutableStateOf(Size.Zero) }

    Canvas(Modifier
        .fillMaxSize()
        .onKeyEvent { event ->
            if(event.type == KeyEventType.KeyDown)
                when(event.key) {
                    Key.W -> selected = (selected - 1).coerceAtLeast(0)
                    Key.S -> selected = (selected + 1).coerceAtMost(options.lastIndex)
                    Key.Spacebar -> {

                        when(selected) {
                            0 -> Birne.dungeons()
                        }

                    }
                }

            true
        }
        .focusRequester(requester)
        .focusable()
    ) {
        resizeScreen(size)
        cellSize = screenSize.height/16f

        drawText(
            text = gameTitle,
            left = { i -> (size.width - gameTitle.length*cellSize/2f)/2f + i*cellSize/2f },
            top = { 0.5f * cellSize }
        )
        drawText(
            text = coinsCount,
            left = { i -> (size.width - coinsCount.length*cellSize/2f)/2f + i*cellSize/2f },
            top = { 1.5f * cellSize }
        )

        options.forEachIndexed { i, option ->
            val text = (if(i == selected) "> " else "") + option
            drawText(
                text = text,
                left = { char -> (size.width - text.length*cellSize/2f)/2f + char*cellSize/2f },
                top = { size.height/2f + i * cellSize - .5f * options.size * cellSize }
            )
        }

        drawText(
            text = hint,
            left = { i -> (size.width - hint.length*cellSize/2f)/2f + i*cellSize/2f },
            top = { size.height - 1.5f * cellSize }
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