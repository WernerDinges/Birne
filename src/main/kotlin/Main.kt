import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import screens.*

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Birne (predev 3)",
        state = WindowState(width = 598.dp, height = 598.dp, position = WindowPosition(Alignment.Center)),
        alwaysOnTop = true
    ) {
        Birne.loadGameData()

        val state by Birne.state
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF36271B)
        ) {
            when(state) {
                is Birne.State.GameOver -> GameOverScreen(state as Birne.State.GameOver)
                Birne.State.Menu -> MenuScreen()
                Birne.State.Skins -> SkinsScreen()
                Birne.State.Shop -> ShopScreen()
                Birne.State.Dungeons -> SelectDungeonScreen()
                is Birne.State.Play -> LevelScreen(state as Birne.State.Play)
                is Birne.State.BuyLevel -> BuyLevelScreen((state as Birne.State.BuyLevel).price, (state as Birne.State.BuyLevel).dungeon, (state as Birne.State.BuyLevel).level)
                is Birne.State.BuySkin -> BuySkinScreen((state as Birne.State.BuySkin).price, (state as Birne.State.BuySkin).skinsToPickFrom)
            }
        }
    }
}