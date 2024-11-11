package core.entity.player

import Textures.PLAYER_ALIEN_IDLE
import Textures.PLAYER_ALIEN_JUMP
import Textures.PLAYER_ALIEN_MOVE1
import Textures.PLAYER_ALIEN_MOVE2
import Textures.PLAYER_APPLE_IDLE
import Textures.PLAYER_APPLE_JUMP
import Textures.PLAYER_APPLE_MOVE1
import Textures.PLAYER_APPLE_MOVE2
import Textures.PLAYER_CACTUS_IDLE
import Textures.PLAYER_CACTUS_JUMP
import Textures.PLAYER_CACTUS_MOVE1
import Textures.PLAYER_CACTUS_MOVE2
import Textures.PLAYER_CLASSIC_IDLE
import Textures.PLAYER_CLASSIC_JUMP
import Textures.PLAYER_CLASSIC_MOVE1
import Textures.PLAYER_CLASSIC_MOVE2
import Textures.PLAYER_FRANKIE_IDLE
import Textures.PLAYER_FRANKIE_JUMP
import Textures.PLAYER_FRANKIE_MOVE1
import Textures.PLAYER_FRANKIE_MOVE2
import Textures.PLAYER_GLASSES_IDLE
import Textures.PLAYER_GLASSES_JUMP
import Textures.PLAYER_GLASSES_MOVE1
import Textures.PLAYER_GLASSES_MOVE2
import Textures.PLAYER_GOLDEN_IDLE
import Textures.PLAYER_GOLDEN_JUMP
import Textures.PLAYER_GOLDEN_MOVE1
import Textures.PLAYER_GOLDEN_MOVE2
import Textures.PLAYER_KNIGHT_IDLE
import Textures.PLAYER_KNIGHT_JUMP
import Textures.PLAYER_KNIGHT_MOVE1
import Textures.PLAYER_KNIGHT_MOVE2
import Textures.PLAYER_PIRATE_IDLE
import Textures.PLAYER_PIRATE_JUMP
import Textures.PLAYER_PIRATE_MOVE1
import Textures.PLAYER_PIRATE_MOVE2
import Textures.PLAYER_SHOES_IDLE
import Textures.PLAYER_SHOES_JUMP
import Textures.PLAYER_SHOES_MOVE1
import Textures.PLAYER_SHOES_MOVE2
import Textures.PLAYER_WILDEYE_IDLE
import Textures.PLAYER_WILDEYE_JUMP
import Textures.PLAYER_WILDEYE_MOVE1
import Textures.PLAYER_WILDEYE_MOVE2
import androidx.compose.ui.graphics.painter.BitmapPainter
import kotlinx.serialization.Serializable

/**
 * This sealed interface represents different player skins in the game. Each skin
 * has a unique set of bitmap assets for idle, moving, and jumping states.
 *
 * @property name The name of the player skin.
 * @property idle The texture for the idle state.
 * @property move1 The texture for the first movement state.
 * @property move2 The texture for the second movement state.
 * @property jump The texture for the jumping state.
 */
@Serializable
sealed interface PlayerSkin {

    val name: String
    val idle: BitmapPainter
    val move1: BitmapPainter
    val move2: BitmapPainter
    val jump: BitmapPainter



    @Serializable
    data object Classic : PlayerSkin {
        override val name = "CLASSIC"
        override val idle = PLAYER_CLASSIC_IDLE
        override val move1 = PLAYER_CLASSIC_MOVE1
        override val move2 = PLAYER_CLASSIC_MOVE2
        override val jump = PLAYER_CLASSIC_JUMP
    }
    @Serializable
    data object Cactus : PlayerSkin {
        override val name = "CACTUS"
        override val idle = PLAYER_CACTUS_IDLE
        override val move1 = PLAYER_CACTUS_MOVE1
        override val move2 = PLAYER_CACTUS_MOVE2
        override val jump = PLAYER_CACTUS_JUMP
    }
    @Serializable
    data object Knight : PlayerSkin {
        override val name = "KNIGHT"
        override val idle = PLAYER_KNIGHT_IDLE
        override val move1 = PLAYER_KNIGHT_MOVE1
        override val move2 = PLAYER_KNIGHT_MOVE2
        override val jump = PLAYER_KNIGHT_JUMP
    }
    @Serializable
    data object Apple : PlayerSkin {
        override val name = "APPLE"
        override val idle = PLAYER_APPLE_IDLE
        override val move1 = PLAYER_APPLE_MOVE1
        override val move2 = PLAYER_APPLE_MOVE2
        override val jump = PLAYER_APPLE_JUMP
    }
    @Serializable
    data object Alien : PlayerSkin {
        override val name = "ALIEN"
        override val idle = PLAYER_ALIEN_IDLE
        override val move1 = PLAYER_ALIEN_MOVE1
        override val move2 = PLAYER_ALIEN_MOVE2
        override val jump = PLAYER_ALIEN_JUMP
    }
    @Serializable
    data object Franken : PlayerSkin {
        override val name = "STAR IN A JAR"
        override val idle = PLAYER_FRANKIE_IDLE
        override val move1 = PLAYER_FRANKIE_MOVE1
        override val move2 = PLAYER_FRANKIE_MOVE2
        override val jump = PLAYER_FRANKIE_JUMP
    }
    @Serializable
    data object Pirate : PlayerSkin {
        override val name = "PIRATE HAT"
        override val idle = PLAYER_PIRATE_IDLE
        override val move1 = PLAYER_PIRATE_MOVE1
        override val move2 = PLAYER_PIRATE_MOVE2
        override val jump = PLAYER_PIRATE_JUMP
    }
    @Serializable
    data object Shoes : PlayerSkin {
        override val name = "GOOFY SHOES"
        override val idle = PLAYER_SHOES_IDLE
        override val move1 = PLAYER_SHOES_MOVE1
        override val move2 = PLAYER_SHOES_MOVE2
        override val jump = PLAYER_SHOES_JUMP
    }
    @Serializable
    data object Glasses : PlayerSkin {
        override val name = "COOL GLASSES"
        override val idle = PLAYER_GLASSES_IDLE
        override val move1 = PLAYER_GLASSES_MOVE1
        override val move2 = PLAYER_GLASSES_MOVE2
        override val jump = PLAYER_GLASSES_JUMP
    }
    @Serializable
    data object Golden : PlayerSkin {
        override val name = "GOLDEN PEAR"
        override val idle = PLAYER_GOLDEN_IDLE
        override val move1 = PLAYER_GOLDEN_MOVE1
        override val move2 = PLAYER_GOLDEN_MOVE2
        override val jump = PLAYER_GOLDEN_JUMP
    }
    @Serializable
    data object WildEye : PlayerSkin {
        override val name = "BILL WILD EYE"
        override val idle = PLAYER_WILDEYE_IDLE
        override val move1 = PLAYER_WILDEYE_MOVE1
        override val move2 = PLAYER_WILDEYE_MOVE2
        override val jump = PLAYER_WILDEYE_JUMP
    }

}