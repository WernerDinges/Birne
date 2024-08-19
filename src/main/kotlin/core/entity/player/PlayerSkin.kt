package core.entity.player

import Textures.PLAYER_CLASSIC_IDLE
import Textures.PLAYER_CLASSIC_JUMP
import Textures.PLAYER_CLASSIC_MOVE1
import Textures.PLAYER_CLASSIC_MOVE2
import androidx.compose.ui.graphics.painter.BitmapPainter

sealed interface PlayerSkin {

    val idle: BitmapPainter
    val move1: BitmapPainter
    val move2: BitmapPainter
    val jump: BitmapPainter



    data object Classic : PlayerSkin {
        override val idle = PLAYER_CLASSIC_IDLE
        override val move1 = PLAYER_CLASSIC_MOVE1
        override val move2 = PLAYER_CLASSIC_MOVE2
        override val jump = PLAYER_CLASSIC_JUMP
    }

}