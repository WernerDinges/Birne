import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import core.entity.player.PlayerSkin
import core.game.Game
import core.game.GameBuilder
import core.generationEngine.rumble.RumbleEngine
import gameData.GameData
import gameData.getAppDataDirectory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import java.io.File

object Birne {
    // Just for the initialization. Don't change
    var cellSize = 1000000f
    // Const 100 FPS
    const val FRAME_DURATION = 10L

    // Outliving game data
    var gameData = GameData()

    // Global app state
    var state: MutableState<State> = mutableStateOf(State.Menu)
    sealed interface State {
        data object Menu: State
        data object SelectDungeon: State
        data class GameOver(val dungeon: Int, val difficulty: Int, val coins: Int, val rooms: Int): State
        data class Play(val instance: Game): State
    }

    // Switch from menu to the game
    fun startGame(dungeon: Int, difficulty: Int) {
        val game = buildGame {
            this dungeon dungeon
            this difficulty difficulty
            this engine RumbleEngine
            this skin PlayerSkin.Classic
        }
        state.value = State.Play(game)
    }

    fun dungeons() {
        state.value = State.SelectDungeon
    }

    fun nextLevel() {
        with((state.value as State.Play).instance) {
            levelNumber++
            level = newLevel()
        }
    }

    fun gameOver(dungeon: Int, coins: Int, rooms: Int, difficulty: Int) {
        state.value = State.GameOver(dungeon, difficulty, coins, rooms)
    }

    fun menu() {
        state.value = State.Menu
    }

    private fun buildGame(init: GameBuilder.() -> Unit): Game {
        return GameBuilder()
            .apply(init)
            .build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun saveGameData(scope: GameData.() -> Unit) {
        gameData.apply(scope)
        val byteArray = Cbor.encodeToByteArray(gameData)

        File(getAppDataDirectory()).apply {
            parentFile?.mkdirs()
            writeBytes(byteArray)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun loadGameData() {
        val file = File(getAppDataDirectory())

        if(file.exists()) {
            val bytearray = File(getAppDataDirectory()).readBytes()
            gameData = Cbor.decodeFromByteArray(bytearray)
        }
    }

}