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
fun GameOverScreen(state: Birne.State.GameOver) {
    val requester = remember { FocusRequester() }
    val title by remember { mutableStateOf("GAME OVER") }
    val coinsCount by remember { mutableStateOf("COINS: ${state.coins}") }
    val roomsCount by remember { mutableStateOf("ROOMS: ${state.rooms}")}
    val description by remember { mutableStateOf("[SPACE] MENU") }

    val (screenSize, resizeScreen) = remember { mutableStateOf(Size.Zero) }

    Canvas(
        Modifier
        .fillMaxSize()
        .onKeyEvent { event ->
            if(event.key == Key.Spacebar && event.type == KeyEventType.KeyDown)
                Birne.menu()

            true
        }
        .focusRequester(requester)
        .focusable()
    ) {
        resizeScreen(size)
        cellSize = screenSize.height/16f

        // Game Over title
        drawText(
            text = title,
            left = { i: Int -> (size.width - title.length*cellSize/2f)/2f + i*cellSize/2f },
            top = { size.height/2f - 1.5f * cellSize }
        )

        // Rooms
        drawText(
            text = roomsCount,
            left = { i: Int -> (size.width - roomsCount.length*cellSize/2f)/2f + i*cellSize/2f },
            top = { size.height/2f + cellSize }
        )

        // Coins
        drawText(
            text = coinsCount,
            left = { i: Int -> (size.width - coinsCount.length*cellSize/2f)/2f + i*cellSize/2f },
            top = { size.height/2f + 1.75f * cellSize }
        )

        drawText(
            text = description,
            left = { i: Int -> (size.width - description.length*cellSize/2f)/2f + i*cellSize/2f },
            top = { size.height - 1.5f * cellSize }
        )

    }

    LaunchedEffect(Unit) {
        requester.requestFocus()

        Birne.saveGameData {
            coins += state.coins

            if(dungeons[state.dungeon]!!.hsCoins < state.coins)
                dungeons[state.dungeon]!!.hsCoins = state.coins

            if(dungeons[state.dungeon]!!.hsRooms < state.rooms)
                dungeons[state.dungeon]!!.hsRooms = state.rooms
        }

        while(true) {
            cellSize = screenSize.height/16f

            delay(10L)
        }
    }
}