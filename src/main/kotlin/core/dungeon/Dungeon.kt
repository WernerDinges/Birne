package core.dungeon

import kotlinx.serialization.Serializable

@Serializable
data class Dungeon(
    val title: String,
    var hsRooms: MutableList<Int> = mutableListOf(0, 0, 0, 0, 0, 0),
    var hsCoins: MutableList<Int> = mutableListOf(0, 0, 0, 0, 0, 0),
    val stars: MutableList<Int?> = mutableListOf(null, 0, 0, null, null, null)
)
