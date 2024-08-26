package core.level

import core.entity.collectable.Collectable
import core.entity.enemy.NotPlayable
import core.entity.player.Player

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

    fun exclude(collectable: Collectable) {
        collectables = collectables.filterNot { it == collectable }
    }

}
