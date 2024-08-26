package screens

import Birne
import Birne.cellSize
import Birne.menu
import Textures.STAR_1
import Textures.STAR_2
import Textures.STAR_3
import Textures.STAR_4
import Textures.STAR_5
import Textures.STAR_EMPTY
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
import core.dungeon.Dungeon
import kotlinx.coroutines.delay
import utils.drawText
import utils.sizeOfCell

@Composable
fun SelectDungeonScreen() {
    val requester = remember { FocusRequester() }

    val dungeons by remember { mutableStateOf(Birne.gameData.dungeons) }
    var selectedDungeon by remember { mutableStateOf(0) }

    val title by remember { mutableStateOf("DUNGEONS") }
    val back by remember { mutableStateOf("[ESC]") }
    val left by remember { mutableStateOf("[A]") }
    val right by remember { mutableStateOf("[D]") }
    val start by remember { mutableStateOf("[SPACE] TO START") }

    val (screenSize, resizeScreen) = remember { mutableStateOf(Size.Zero) }

    Canvas(Modifier
        .fillMaxSize()
        .onKeyEvent { event ->
            if(event.type == KeyEventType.KeyDown)
                when(event.key) {
                    Key.Escape -> menu()
                    Key.A -> selectedDungeon = (selectedDungeon - 1).coerceAtLeast(0)
                    Key.D -> selectedDungeon = (selectedDungeon + 1).coerceAtMost(dungeons.lastIndex)
                    Key.Spacebar -> if(dungeons[selectedDungeon] != null) Birne.startGame(selectedDungeon)
                }

            true
        }
        .focusRequester(requester)
        .focusable()
    ) {
        resizeScreen(size)
        cellSize = screenSize.height/16f

        // Title
        drawText(
            text = title,
            left = { char -> (size.width - title.length*cellSize/2f)/2f + char * cellSize/2f },
            top = { 0.5f * cellSize }
        )

        // Arrows
        if(selectedDungeon > 0)
            drawText(
                text = left,
                left = { char -> cellSize/2f + char*cellSize/2f },
                top = { size.height/2f - cellSize/4f }
            )
        if(selectedDungeon < dungeons.lastIndex)
            drawText(
                text = right,
                left = { char -> size.width - (1f + right.length)*cellSize/2f + char*cellSize/2f },
                top = { size.height/2f - cellSize/4f }
            )
        // Back
        drawText(
            text = back,
            left = { char -> (char + 1) * cellSize/2f },
            top = { cellSize/2f }
        )

        // Dungeon display
        if(dungeons[selectedDungeon] != null) dungeons[selectedDungeon].also {
            val hs = "HS: ${it!!.hsRooms} R, ${it.hsCoins} C"

            drawText(
                text = it.title,
                left = { i -> (size.width - it.title.length*cellSize/2f)/2f + i*cellSize/2f },
                top = { size.height/2f - 1.5f * cellSize }
            )
            drawText(
                text = hs,
                left = { i -> (size.width - hs.length*cellSize/2f)/2f + i*cellSize/2f },
                top = { size.height/2f - cellSize/4f }
            )

            it.stars.forEachIndexed { i, star ->
                with(when(star) {
                    1 -> STAR_1
                    2 -> STAR_2
                    3 -> STAR_3
                    4 -> STAR_4
                    5 -> STAR_5
                    else -> STAR_EMPTY
                }) {
                    translate(
                        left = size.width/2f - cellSize * (3 - i),
                        top = size.height/2f + cellSize
                    ) {
                        draw(sizeOfCell())
                    }
                }
            }

            drawText(
                text = start,
                left = { i -> (size.width - start.length*cellSize/2f) / 2f + i*cellSize/2f },
                top = { size.height - cellSize }
            )

        }
        // Coming soon
        else {

            val text = "COMING SOON..."
            drawText(
                text = text,
                left = { i -> (size.width - text.length * cellSize / 2f) / 2f + i * cellSize / 2f },
                top = { size.height / 2f - cellSize / 4f }
            )

        }
    }

    LaunchedEffect(Unit) {
        requester.requestFocus()

        while(true) {
            cellSize = screenSize.height/16f

            delay(10L)
        }
    }
}