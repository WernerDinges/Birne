package screens

import Birne.cellSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.key.*
import core.entity.player.PlayerSkin
import kotlinx.coroutines.delay
import utils.drawText
import utils.sizeOfCell

/**
 * Composable function that displays the screen for buying a new player skin.
 * The screen displays the new skin along with its name and provides a prompt to exit.
 * The player's game data is updated to reflect the purchase.
 *
 * @param price  The cost of the skin that the player is purchasing.
 * @param skinsToPickFrom  A list of available player skins to choose from.
 */
@Composable
fun BuySkinScreen(price: Int, skinsToPickFrom: List<PlayerSkin>) {
    val requester = remember { FocusRequester() }

    val skin by remember { mutableStateOf(skinsToPickFrom.random()) }

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

        val title = "NEW SKIN UNLOCKED"
        drawText(
            text = title,
            left = { i: Int -> (size.width - title.length*cellSize/2f)/2f + i*cellSize/2f },
            top = { .5f*cellSize }
        )

        val description = "[ESC]"
        drawText(
            text = description,
            left = { char -> (char + 1) * cellSize/2f },
            top = { cellSize/2f }
        )

        with(skin.idle) {
            translate(
                left = (size.width - cellSize)/2f,
                top = (size.height - cellSize)/2f
            ) {
                draw(sizeOfCell())
            }
        }
        val unlocked = skin.name
        drawText(
            text = unlocked,
            left = { i: Int -> (size.width - unlocked.length*cellSize/2f)/2f + i*cellSize/2f },
            top = { size.height/2f + 1.75f*cellSize }
        )

    }

    LaunchedEffect(Unit) {
        requester.requestFocus()

        Birne.saveGameData {
            coins -= price
            skins += skin
        }

        while(true) {
            cellSize = screenSize.height/16f

            delay(10L)
        }
    }
}