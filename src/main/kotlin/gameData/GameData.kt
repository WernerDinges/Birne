package gameData

import core.dungeon.Dungeon
import kotlinx.serialization.Serializable

@Serializable
data class GameData(
    var coins: Int = 0,
    var dungeons: MutableList<Dungeon?> = mutableListOf(
        Dungeon("COZY CORRIDORS"),
        null
    )
)