package core.level

import core.entity.collectable.Collectable
import core.entity.notPlayable.NotPlayable

/**
 * Configuration class representing the properties of a game level.
 *
 * @property number The level number.
 * @property difficulty The difficulty rating of the level.
 * @property mapSize A pair representing the width and height of the level map.
 * @property startPoint A pair representing the starting coordinates of the player in the level.
 * @property endPoint A pair representing the ending coordinates of the level.
 * @property tileSkeleton A 2D array representing the layout and structure of the level tiles.
 * @property notPlayableEntities A list of entities that are not controlled by the player.
 * @property collectables A list of items that can be collected within the level.
 * @property doorClosed A boolean indicating if the level's exit door is initially closed.
 */
data class LevelConfig(
    var number: Int,
    var difficulty: Int,
    var mapSize: Pair<Int, Int>,
    var startPoint: Pair<Int, Int>,
    var endPoint: Pair<Int, Int>,
    var tileSkeleton: Array<Array<Int>>,
    var notPlayableEntities: List<NotPlayable>,
    var collectables: List<Collectable>,
    var doorClosed: Boolean
) {

    /**
     * Removes the specified collectable from the list of collectables in the level configuration.
     *
     * @param collectable The collectable entity that is to be removed from the level.
     */
    fun exclude(collectable: Collectable) {
        collectables = collectables.filterNot { it == collectable }
    }
    /**
     * Excludes a specified `NotPlayable` entity from the list of non-playable entities
     * in the current `LevelConfig`.
     *
     * @param entity The `NotPlayable` entity to be excluded from the level configuration.
     */
    fun exclude(entity: NotPlayable) {
        notPlayableEntities = notPlayableEntities.filterNot { it == entity }
    }

}
