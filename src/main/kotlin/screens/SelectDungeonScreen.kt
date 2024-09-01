package screens

import Birne
import Birne.cellSize
import Textures.SELECT_UP
import Textures.STAR_1
import Textures.STAR_2
import Textures.STAR_3
import Textures.STAR_4
import Textures.STAR_5
import Textures.STAR_CLOSED
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
import kotlinx.coroutines.delay
import utils.drawText
import utils.sizeOfCell

@Composable
fun SelectDungeonScreen() {
    val requester = remember { FocusRequester() }

    val dungeons by remember { mutableStateOf(Birne.gameData.dungeons) }
    var selectedDungeon by remember { mutableStateOf(0) }

    var difficulty: Int? by remember { mutableStateOf(null) }

    val (screenSize, resizeScreen) = remember { mutableStateOf(Size.Zero) }

    Canvas(Modifier
        .fillMaxSize()
        .onKeyEvent { event ->
            if(event.type == KeyEventType.KeyDown)
                when(event.key) {
                    Key.Escape -> if(difficulty == null) Birne.menu() else difficulty = null
                    Key.A -> {
                        if(difficulty == null)
                            selectedDungeon = (selectedDungeon - 1).coerceAtLeast(0)
                        else
                            difficulty = (difficulty!! - 1).coerceAtLeast(1)
                    }
                    Key.D -> {
                        if(difficulty == null)
                            selectedDungeon = (selectedDungeon + 1).coerceAtMost(dungeons.lastIndex)
                        else
                            difficulty = (difficulty!! + 1).coerceAtMost(6)
                    }
                    Key.Spacebar -> {
                        if(dungeons[selectedDungeon] != null)
                            if(difficulty != null) {
                                if(dungeons[selectedDungeon]!!.stars[difficulty!!-1] != null)
                                    Birne.startGame(selectedDungeon, difficulty!!)
                            } else {
                                difficulty = 1
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

        val title = "DUNGEONS"
        drawText(
            text = title,
            left = { char -> (size.width - title.length*cellSize/2f)/2f + char * cellSize/2f },
            top = { 0.5f * cellSize }
        )

        // Arrows
        if((selectedDungeon > 0 && difficulty == null) || ((difficulty ?: 0) > 1))
            drawText(
                text = "[A]",
                left = { char -> cellSize/2f + char*cellSize/2f },
                top = { size.height/2f - cellSize/4f }
            )
        if((selectedDungeon < dungeons.lastIndex && difficulty == null) || ((difficulty ?: 7) < 6))
            drawText(
                text = "[D]",
                left = { char -> size.width - 4f*cellSize/2f + char*cellSize/2f },
                top = { size.height/2f - cellSize/4f }
            )
        // Back
        drawText(
            text = "[ESC]",
            left = { char -> (char + 1) * cellSize/2f },
            top = { cellSize/2f }
        )

        // Dungeon display
        if(dungeons[selectedDungeon] != null) dungeons[selectedDungeon].also {

            drawText(
                text = it!!.title,
                left = { i -> (size.width - it.title.length*cellSize/2f)/2f + i*cellSize/2f },
                top = { size.height/2f - 1.5f * cellSize }
            )
            if(difficulty != null) {
                val hs = "HS: ${it.hsRooms[difficulty!!-1]} R, ${it.hsCoins[difficulty!!-1]} C"
                drawText(
                    text = hs,
                    left = { i -> (size.width - hs.length * cellSize / 2f) / 2f + i * cellSize / 2f },
                    top = { size.height / 2f + 1.25f*cellSize }
                )
            }

            it.stars.forEachIndexed { i, star ->
                with(when(star) {
                    null -> STAR_CLOSED
                    0 -> STAR_EMPTY
                    1 -> STAR_1
                    2 -> STAR_2
                    3 -> STAR_3
                    4 -> STAR_4
                    5 -> STAR_5
                    else -> STAR_EMPTY
                }) {
                    translate(
                        left = size.width/2f - cellSize * (3 - i),
                        top = size.height/2f - cellSize/2f
                    ) {
                        draw(sizeOfCell())
                    }
                }
            }
            if(difficulty != null)
                with(SELECT_UP) {
                    translate(
                        left = size.width/2f - cellSize * (4 - difficulty!!),
                        top = size.height/2f + cellSize/2f
                    ) {
                        draw(sizeOfCell())
                    }
                }

            val hint =
                if(difficulty == null)
                    "[SPACE] TO SELECT"
                else {
                    if(dungeons[selectedDungeon]!!.stars[difficulty!!-1] != null)
                        "[SPACE] TO START"
                    else
                        "LOCKED"
                }
            drawText(
                text = hint,
                left = { i -> (size.width - hint.length*cellSize/2f) / 2f + i*cellSize/2f },
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