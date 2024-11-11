package core.dungeon

import kotlinx.serialization.Serializable

/**
 * Represents a dungeon within the game, including title, high scores for rooms and coins, and star ratings.
 *
 * @property title The title of the dungeon.
 * @property hsRooms Mutable list holding high scores for the dungeon rooms.
 * @property hsCoins Mutable list holding high scores for coins collected in the dungeon.
 * @property stars Mutable list holding star ratings for the dungeon, where a value of `null` indicates no rating.
 */
@Serializable
data class Dungeon(
    val title: String,
    var hsRooms: MutableList<Int> = mutableListOf(0, 0, 0, 0, 0, 0),
    var hsCoins: MutableList<Int> = mutableListOf(0, 0, 0, 0, 0, 0),
    val stars: MutableList<Int?> = mutableListOf(0, null, null, null, null, null)
)