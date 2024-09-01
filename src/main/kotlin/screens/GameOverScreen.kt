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

        val title = "GAME OVER"
        drawText(
            text = title,
            left = { i: Int -> (size.width - title.length*cellSize/2f)/2f + i*cellSize/2f },
            top = { size.height/2f - 1.5f * cellSize }
        )

        val roomsCount = "ROOMS: ${state.rooms}"
        drawText(
            text = roomsCount,
            left = { i: Int -> (size.width - roomsCount.length*cellSize/2f)/2f + i*cellSize/2f },
            top = { size.height/2f + cellSize }
        )

        val coinsCount = "COINS: ${state.coins}"
        drawText(
            text = coinsCount,
            left = { i: Int -> (size.width - coinsCount.length*cellSize/2f)/2f + i*cellSize/2f },
            top = { size.height/2f + 1.75f * cellSize }
        )

        val description = "[ESC] MENU"
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

            if(dungeons[state.dungeon]!!.hsCoins[state.difficulty-1] < state.coins)
                dungeons[state.dungeon]!!.hsCoins[state.difficulty-1] = state.coins

            if(dungeons[state.dungeon]!!.hsRooms[state.difficulty-1] < state.rooms)
                dungeons[state.dungeon]!!.hsRooms[state.difficulty-1] = state.rooms

            if(dungeons[state.dungeon]!!.stars[state.difficulty-1] == 0)
                if(state.rooms >= 100)
                    dungeons[state.dungeon]!!.stars[state.difficulty-1] = 1
        }

        while(true) {
            cellSize = screenSize.height/16f

            delay(10L)
        }
    }
}