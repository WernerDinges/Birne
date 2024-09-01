package screens

import Birne.cellSize
import Textures.STAR_EMPTY
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
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.key.*
import kotlinx.coroutines.delay
import utils.drawText
import utils.sizeOfCell

@Composable
fun BuyLevelScreen(price: Int, dungeon: Int, difficulty: Int) {
    val requester = remember { FocusRequester() }

    val (screenSize, resizeScreen) = remember { mutableStateOf(Size.Zero) }

    Canvas(
        Modifier
            .fillMaxSize()
            .onKeyEvent { event ->
                if(event.key == Key.Escape && event.type == KeyEventType.KeyDown)
                    Birne.menu()

                true
            }
            .focusRequester(requester)
            .focusable()
    ) {
        resizeScreen(size)
        cellSize = screenSize.height/16f

        val title = "NEW LEVEL UNLOCKED"
        drawText(
            text = title,
            left = { i: Int -> (size.width - title.length*cellSize/2f)/2f + i*cellSize/2f },
            top = { size.height/2f - 1.5f * cellSize }
        )

        val description = "[ESC]"
        drawText(
            text = description,
            left = { char -> (char + 1) * cellSize/2f },
            top = { cellSize/2f }
        )

        with(STAR_EMPTY) {
            translate(
                left = (size.width - cellSize)/2f,
                top = (size.height - cellSize)/2f
            ) {
                draw(sizeOfCell())
            }
        }

        val unlocked = "LEVEL: $difficulty"
        drawText(
            text = unlocked,
            left = { i: Int -> (size.width - unlocked.length*cellSize/2f)/2f + i*cellSize/2f },
            top = { size.height/2f + 1.5f*cellSize }
        )
        val dung = "OF ${when(dungeon) {
            0 -> "COZY CORRIDORS"
            else -> "[UNKNOWN]"
        }}"
        drawText(
            text = dung,
            left = { i: Int -> (size.width - dung.length*cellSize/2f)/2f + i*cellSize/2f },
            top = { size.height/2f + 2f*cellSize }
        )

    }

    LaunchedEffect(Unit) {
        requester.requestFocus()

        Birne.saveGameData {
            coins -= price
            dungeons[dungeon]!!.stars[difficulty-1] = 0
        }

        while(true) {
            cellSize = screenSize.height/16f

            delay(10L)
        }
    }
}