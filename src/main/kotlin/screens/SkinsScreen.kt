package screens

import Birne
import Birne.cellSize
import Birne.gameData
import Textures.PLACEHOLDER
import Textures.PLACEHOLDER_SELECTED
import Textures.UNKNOWN
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import core.entity.player.playerSkinList
import kotlinx.coroutines.delay
import utils.drawText
import utils.sizeOfCell

/**
 * Displays the Skin Selection Screen for the game.
 * Users can navigate through the available skins using the W, A, S, D keys, and select a skin using the Spacebar.
 * The skin selection screen displays a grid of available skins and allows the user to choose one.
 * The Escape key can be used to return to the main menu.
 *
 * Controls:
 * - W: Move up in the skin grid
 * - A: Move left in the skin grid
 * - S: Move down in the skin grid
 * - D: Move right in the skin grid
 * - Spacebar: Select the highlighted skin
 * - Escape: Return to main menu
 *
 * The screen features:
 * - A title "SKINS"
 * - A back button labeled "[ESC]"
 * - The available skins in a grid layout
 * - Hints and status messages for the currently selected skin
 *
 * The skin selection screen uses a focus requester to capture keyboard events and interact with the user.
 * The screen is redrawn to reflect the position of the selected skin and provide appropriate hints and status messages.
 */
@Composable
fun SkinsScreen() {
    val requester = remember { FocusRequester() }

    var x by remember { mutableStateOf(0) }
    var y by remember { mutableStateOf(0) }
    var itemOffsetY by remember { mutableStateOf(0) }
    val itemsHeight = 5
    val itemsWidth = 5
    val skins by remember { mutableStateOf(playerSkinList().map { if(it in gameData.skins) it else null }) }

    var hint by remember { mutableStateOf("") }

    val (screenSize, resizeScreen) = remember { mutableStateOf(Size.Zero) }
    var trigger by remember { mutableStateOf(0.dp) }

    Canvas(Modifier
        .offset(x = trigger)
        .fillMaxSize()
        .onKeyEvent { event ->
            if(event.type == KeyEventType.KeyDown)
                when(event.key) {
                    Key.Escape -> Birne.menu()
                    Key.W -> {
                        y = (y - 1).coerceAtLeast(0)
                        if(y < itemOffsetY)
                            itemOffsetY = y
                    }
                    Key.S -> {
                        if((y+1)*itemsWidth+x <= skins.lastIndex)
                            y++
                        if(y > itemOffsetY + itemsHeight)
                            itemOffsetY = y - itemsHeight + 1
                    }
                    Key.A -> x = (x - 1).coerceAtLeast(0)
                    Key.D -> x = (x + 1).coerceAtMost(
                        if(y*itemsWidth+x < skins.lastIndex) itemsWidth - 1 else skins.lastIndex % itemsWidth
                    )
                    Key.Spacebar -> if(skins[y*itemsWidth+x] != null) Birne.saveGameData {
                        selectedSkin = skins[y*itemsWidth+x]!!
                        trigger = 1.dp
                        trigger = 0.dp
                    }
                }
            true
        }
        .focusRequester(requester)
        .focusable()
    ) {
        resizeScreen(size)
        cellSize = screenSize.height/16f

        val title = "SKINS"
        drawText(
            text = title,
            left = { char -> (size.width - title.length*cellSize/2f)/2f + char * cellSize/2f },
            top = { 0.5f * cellSize }
        )
        val back = "[ESC]"
        drawText(
            text = back,
            left = { char -> (char + 1) * cellSize/2f },
            top = { cellSize/2f }
        )

        val skinsToDisplay = (itemOffsetY*itemsWidth)..(((itemOffsetY+itemsHeight)*itemsWidth).coerceAtMost(skins.lastIndex))
        val skinsOffset = Offset(
            x = (size.width - itemsWidth*cellSize*2f)/2f,
            y = (size.height - itemsHeight* cellSize*2f)/2f
        )
        skins.slice(skinsToDisplay).forEachIndexed { i, skin ->
            val isSelected = y*itemsWidth+x == i
            val offsetX = (i % itemsWidth) * cellSize*2f
            val offsetY = (i / itemsWidth) * cellSize*2f

            with(if(isSelected) PLACEHOLDER_SELECTED else PLACEHOLDER) {
                translate(skinsOffset.x + offsetX, skinsOffset.y + offsetY) {
                    draw(sizeOfCell()*2f)
                }
            }
            with(skin?.idle ?: UNKNOWN) {
                translate(
                    skinsOffset.x + offsetX + cellSize/2f,
                    skinsOffset.y + offsetY + cellSize/(if(skin != null) 4f else 2.5f)
                ) {
                    draw(sizeOfCell())
                }
            }

            if(i == y*itemsWidth + x) {

                if (skin != null) {
                    val skinName = skin.name
                    drawText(
                        text = skinName,
                        left = { char -> (size.width - skinName.length * cellSize / 2f) / 2f + char * cellSize / 2f },
                        top = { size.height - 2f * cellSize }
                    )
                }
                hint = when(skin) {
                    null -> "UNKNOWN"
                    gameData.selectedSkin -> "SELECTED"
                    else -> "[SPACE] TO SELECT"
                }
                drawText(
                    text = hint,
                    left = { char -> (size.width - hint.length * cellSize / 2f) / 2f + char * cellSize / 2f },
                    top = { size.height - cellSize }
                )

            }
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