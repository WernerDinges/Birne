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
import screens.GameOverScreen
import screens.LevelScreen
import screens.MenuScreen
import screens.SelectDungeonScreen

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Birne (predev 3)",
        state = WindowState(width = 598.dp, height = 598.dp, position = WindowPosition(Alignment.Center))
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
                Birne.State.SelectDungeon -> SelectDungeonScreen()
                is Birne.State.Play -> LevelScreen(state as Birne.State.Play)
            }
        }
    }
}