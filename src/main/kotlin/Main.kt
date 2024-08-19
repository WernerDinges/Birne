import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import core.game.Game
import screens.GameOverScreen
import screens.LevelScreen
import screens.MenuScreen

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Birne (predev 2)",
        state = WindowState(width = 598.dp, height = 598.dp, position = WindowPosition(Alignment.Center))
    ) {
        val state by Birne.state
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF36271B)
        ) {
            println(state)
            when(state) {
                Birne.State.GameOver -> GameOverScreen()
                Birne.State.Menu -> MenuScreen()
                is Game -> LevelScreen(state as Game)
            }
        }
    }
}