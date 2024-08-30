package core.entity.notPlayable

import androidx.compose.ui.geometry.Offset
import core.entity.Entity
import core.entity.player.Player
import core.level.LevelConfig

interface NotPlayable: Entity {

    var vx: Float
    var vy: Float

    val collision: Boolean

    var ticks: Long
    var isMirrored: Boolean

    fun thinkAndAct(millis: Long, skeleton: Array<Array<Int>>, level: LevelConfig, player: Player)

}