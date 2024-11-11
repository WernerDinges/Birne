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

/**
 * Birne object manages the overall game state and handles transitions between different screens of the game.
 *
 * The object controls various states such as the main menu, dungeon selection, skin selection, and the shop.
 * It also manages game initialization, transitions between game states, and saving/loading game data.
 */
object Birne {
    // Game version (for window title only)
    const val VERSION = "predev 4"

    // Just for the initialization. Don't change
    var cellSize = 1000000f
    // Const 100 FPS
    const val FRAME_DURATION = 10L

    // Outliving game data
    var gameData = GameData()

    // Global app state
    var state: MutableState<State> = mutableStateOf(State.Menu)
    /**
     * Represents different states in the game.
     */
    sealed interface State {
        /**
         * Represents the main menu state in the application where the user can navigate to other sections
         * like Dungeons, Skins, or Shop. This is a part of the State sealed interface that handles various
         * states of the application.
         */
        data object Menu : State
        /**
         * Represents the state where the player is in the dungeons selection menu.
         *
         * This is used in the game to transition to the screen where the player can select
         * different dungeons to play through.
         *
         * Part of the State interface which includes other states like Menu, Skins, Shop, and others.
         */
        data object Dungeons : State
        /**
         * Represents the state where skins can be selected or changed in the application.
         */
        data object Skins : State
        /**
         * Represents the state of the game where players can interact with the shop.
         *
         * The Shop is a specific state within the game where players can purchase
         * items, skins, or upgrades. This is part of the overall state management
         * system of the game, allowing for a modular and extendable design.
         *
         * The Shop state is used to manage and transition to the shop interface,
         * ensuring that all interactions and data related to the shop are encapsulated
         * within this state.
         */
        data object Shop : State
        /**
         * Represents the state where the user can buy a skin in the game shop.
         *
         * @property price The price of the skin to be purchased.
         * @property skinsToPickFrom The list of available skins from which the user can choose.
         */
        data class BuySkin(val price: Int, val skinsToPickFrom: List<PlayerSkin>): State
        /**
         * Represents the state when a user is purchasing a level.
         *
         * @property price The cost of the level being purchased.
         * @property dungeon The identifier of the dungeon where the level is located.
         * @property level The specific level number being purchased.
         */
        data class BuyLevel(val price: Int, val dungeon: Int, val level: Int): State
        /**
         * Data class representing the state of the game when it is over.
         *
         * @property dungeon The dungeon level the player reached.
         * @property difficulty The difficulty level at which the game was played.
         * @property coins The number of coins collected by the player.
         * @property rooms The number of rooms explored by the player.
         */
        data class GameOver(val dungeon: Int, val difficulty: Int, val coins: Int, val rooms: Int): State
        /**
         * Represents the state of the game where the player is actively playing a level.
         *
         * @property instance The current game instance that holds all relevant game data like levels, configs, and to handle key events.
         */
        data class Play(val instance: Game): State
    }

    // Switch from menu to the game
    fun startGame(dng: Int, dff: Int) {
        val game = buildGame {
            this dungeon dng
            this difficulty dff
            this engine RumbleEngine
            this skin gameData.selectedSkin
        }
        state.value = State.Play(game)
    }
    fun buySkin(price: Int, skinsToPickFrom: List<PlayerSkin>) {
        state.value = State.BuySkin(price, skinsToPickFrom)
    }
    fun buyLevel(price: Int, dungeon: Int, level: Int) {
        state.value = State.BuyLevel(price, dungeon, level)
    }
    fun dungeons() { state.value = State.Dungeons }
    fun shop() { state.value = State.Shop }
    fun skins() { state.value = State.Skins }
    fun nextLevel() {
        with((state.value as State.Play).instance) {
            levelNumber++
            level = newLevel()
        }
    }
    fun gameOver(dungeon: Int, coins: Int, rooms: Int, difficulty: Int) {
        state.value = State.GameOver(dungeon, difficulty, coins, rooms)
    }
    fun menu() { state.value = State.Menu }

    private fun buildGame(init: GameBuilder.() -> Unit): Game {
        return GameBuilder()
            .apply(init)
            .build()
    }

    /**
     * Saves the game data to the appropriate application directory in CBOR format.
     * This method modifies the current game data using the provided scope,
     * encodes it using CBOR, and writes it to a file.
     * The file location is determined based on the operating system.
     *
     * @param scope A lambda with receiver of type GameData. This lambda is used to modify the game data before saving.
     */
    @OptIn(ExperimentalSerializationApi::class)
    fun saveGameData(scope: GameData.() -> Unit) {
        gameData.apply(scope)
        val byteArray = Cbor.encodeToByteArray(gameData)

        File(getAppDataDirectory()).apply {
            parentFile?.mkdirs()
            writeBytes(byteArray)
        }
    }

    /**
     * Loads game data from the file located in the application data directory and decodes it from CBOR format.
     *
     * The method gets the application data directory path, reads the byte array from the file,
     * and deserializes it into the gameData object using CBOR.
     * The method uses experimental serialization API.
     */
    @OptIn(ExperimentalSerializationApi::class)
    fun loadGameData() {
        val file = File(getAppDataDirectory())

        if(file.exists()) {
            val bytearray = File(getAppDataDirectory()).readBytes()
            gameData = Cbor.decodeFromByteArray(bytearray)
        }
    }

}