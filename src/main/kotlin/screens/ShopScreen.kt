package screens

import Birne
import Birne.cellSize
import Birne.gameData
import Textures.PLACEHOLDER
import Textures.PLACEHOLDER_SELECTED
import Textures.RANDOM_SKIN
import Textures.STAR_CLOSED
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.input.key.*
import core.entity.player.PlayerSkin
import core.entity.player.playerSkinList
import kotlinx.coroutines.delay
import utils.drawText
import utils.sizeOfCell
import kotlin.math.sqrt

@Composable
fun ShopScreen() {
    val requester = remember { FocusRequester() }

    val itemsMaxWidth = 6
    val skinCost = ( 50 * sqrt(gameData.skins.size.toFloat()) ).toInt()
    val items by remember { mutableStateOf(mutableListOf<ShopItem>().apply {
        val skins = playerSkinList().filter { it !in gameData.skins }
        if(skins.isNotEmpty())
            this += ShopItem.RandomSkin(skinCost, skins)

        if(gameData.dungeons[0]!!.stars.contains(null)) {
            val prices = listOf(0, 30, 60, 80, 90, 100)
            val index = gameData.dungeons[0]!!.stars.indexOfFirst { it == null }
            this += ShopItem.CozyCorridorsNewDifficulty(
                prices[index],
                index+1,
                gameData.dungeons[0]!!.stars[index-1] in setOf(null, 0)
            )
        }
    }) }
    var selectedItem by remember { mutableStateOf(0) }

    val (screenSize, resizeScreen) = remember { mutableStateOf(Size.Zero) }

    Canvas(Modifier
        .fillMaxSize()
        .onKeyEvent { event ->
            if(event.type == KeyEventType.KeyDown)
                when(event.key) {
                    Key.Escape -> Birne.menu()
                    Key.A -> selectedItem = (selectedItem - 1).coerceAtLeast(0)
                    Key.D -> selectedItem = (selectedItem + 1).coerceAtMost(items.lastIndex)
                    Key.Spacebar -> {
                        val item = items[selectedItem]
                        if(item.available)
                            item.buy()
                    }
                }
            true
        }
        .focusRequester(requester)
        .focusable()
    ) {
        resizeScreen(size)
        cellSize = screenSize.height/16f

        val title = "SHOP"
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
        val coins = "${gameData.coins}"
        drawText(
            text = coins,
            left = { i -> size.width - cellSize/2f * (coins.length + 1) + i * cellSize/2f },
            top = { cellSize/2f }
        )

        val itemsOffsetX = (size.width - items.size* cellSize*2f)/2f
        items.forEachIndexed { i, item ->
            val isSelected = selectedItem == i
            val offsetX = itemsOffsetX + i*cellSize*2f
            val offsetY = cellSize*2f

            with(if(isSelected) PLACEHOLDER_SELECTED else PLACEHOLDER) {
                translate(offsetX, offsetY) {
                    draw(sizeOfCell()*2f)
                }
            }
            with(item.icon) {
                translate(
                    offsetX + cellSize/2f,
                    offsetY + cellSize/2.5f
                ) {
                    draw(sizeOfCell())
                }
            }

            if(isSelected) {

                drawText(
                    text = item.name,
                    left = { char -> (size.width - item.name.length * cellSize / 2f) / 2f + char * cellSize / 2f },
                    top = { 5f * cellSize }
                )
                item.description.forEachIndexed { index, string ->
                    drawText(
                        text = string,
                        left = { char -> cellSize + char * cellSize/2f },
                        top = { (7f + index/2f) * cellSize }
                    )
                }

                val price = "${item.price}"
                drawText(
                    text = price,
                    left = { char -> (size.width - price.length * cellSize / 2f) / 2f + char * cellSize / 2f },
                    top = { size.height - 2f*cellSize }
                )
                val hint = if(item.available) "[SPACE] TO BUY" else "UNAVAILABLE"
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

sealed interface ShopItem {
    val name: String
    val description: List<String>
    val price: Int
    val icon: BitmapPainter
    val available: Boolean
    val buy: () -> Unit

    data class RandomSkin(override val price: Int, val skinsToPick: List<PlayerSkin>): ShopItem {
        override val name = "SKIN"
        override val description = listOf("UNLOCK A RANDOM SKIN")
        override val icon = RANDOM_SKIN
        override val available = price <= gameData.coins
        override val buy: () -> Unit = { Birne.buySkin(price, skinsToPick) }
    }
    data class CozyCorridorsNewDifficulty(
        override val price: Int,
        val difficulty: Int,
        val need100: Boolean
    ): ShopItem {
        override val name = "COZY CORRIDORS"
        override val description = listOf(
            "UNLOCK THE NEXT LEVEL: $difficulty",
            if(need100) "YOU NEED TO REACH A HIGHSCORE" else "",
            if(need100) "OF 100 ROOMS ON THE" else "",
            if(need100) "PREVIOUS LEVEL: ${difficulty-1}" else ""
        )
        override val icon = STAR_CLOSED
        override val available = price <= gameData.coins && !need100
        override val buy: () -> Unit = { Birne.buyLevel(price, 0, difficulty) }
    }
}