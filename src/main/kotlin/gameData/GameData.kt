package gameData

import core.dungeon.Dungeon
import core.entity.player.PlayerSkin
import kotlinx.serialization.Serializable

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