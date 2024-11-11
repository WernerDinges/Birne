package gameData

import core.dungeon.Dungeon
import core.entity.player.PlayerSkin
import kotlinx.serialization.Serializable

/**
 * Data class representing the state of the game.
 *
 * @property coins Number of coins collected by the player.
 * @property dungeons List of dungeons available in the game, including the initial dungeon.
 * @property skins Set of skins available for the player.
 * @property selectedSkin Skin currently selected by the player.
 */
@Serializable
data class GameData(
    var coins: Int = 0,
    var dungeons: MutableList<Dungeon?> = mutableListOf(
        Dungeon("1. COZY CORRIDORS"),
        null
    ),
    var skins: Set<PlayerSkin> = setOf(PlayerSkin.Classic),
    var selectedSkin: PlayerSkin = PlayerSkin.Classic
)