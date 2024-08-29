package core.entity.notPlayable

import androidx.compose.ui.geometry.Offset
import core.entity.Entity

interface NotPlayable: Entity {

    var vx: Float
    var vy: Float

    var ticks: Long
    var isMirrored: Boolean

    fun thinkAndAct(millis: Long, skeleton: Array<Array<Int>>, playerPosition: Offset)

}