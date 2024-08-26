package core.dungeon

import kotlinx.serialization.Serializable

@Serializable
data class Dungeon(
    val title: String,
    var hsRooms: Int = 0,
    var hsCoins: Int = 0,
    val stars: MutableList<Int> = mutableListOf(0, 0, 0, 0, 0, 0)
)
