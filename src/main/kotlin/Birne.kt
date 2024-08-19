import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object Birne {

    // Just for the initialization. Don't change
    var cellSize = 1000000f

    var frameDuration = 10L

    var state: MutableState<State> = mutableStateOf(State.Menu)

    interface State {
        data object Menu: State
        data object GameOver: State
        //Game
    }

}