import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource

object Textures {

    val STAR_CLOSED = BitmapPainter(useResource("star_closed.png", ::loadImageBitmap))
    val STAR_EMPTY = BitmapPainter(useResource("star_empty.png", ::loadImageBitmap))
    val STAR_1 = BitmapPainter(useResource("star_1.png", ::loadImageBitmap))
    val STAR_2 = BitmapPainter(useResource("star_2.png", ::loadImageBitmap))
    val STAR_3 = BitmapPainter(useResource("star_3.png", ::loadImageBitmap))
    val STAR_4 = BitmapPainter(useResource("star_4.png", ::loadImageBitmap))
    val STAR_5 = BitmapPainter(useResource("star_5.png", ::loadImageBitmap))
    val SELECT_UP = BitmapPainter(useResource("select_up.png", ::loadImageBitmap))

    val HEART = BitmapPainter(useResource("heart.png", ::loadImageBitmap))
    val HEART_EMPTY = BitmapPainter(useResource("heart_empty.png", ::loadImageBitmap))
    val JUMP = BitmapPainter(useResource("jump.png", ::loadImageBitmap))
    val JUMP_EMPTY = BitmapPainter(useResource("jump_empty.png", ::loadImageBitmap))

    val PLAYER_CLASSIC_IDLE = BitmapPainter(useResource("player_classic_idle.png", ::loadImageBitmap))
    val PLAYER_CLASSIC_MOVE1 = BitmapPainter(useResource("player_classic_move1.png", ::loadImageBitmap))
    val PLAYER_CLASSIC_MOVE2 = BitmapPainter(useResource("player_classic_move2.png", ::loadImageBitmap))
    val PLAYER_CLASSIC_JUMP = BitmapPainter(useResource("player_classic_jump.png", ::loadImageBitmap))

    val WALKER1 = BitmapPainter(useResource("walker1.png", ::loadImageBitmap))
    val WALKER2 = BitmapPainter(useResource("walker2.png", ::loadImageBitmap))

    val HOPPER = BitmapPainter(useResource("hopper.png", ::loadImageBitmap))
    val HOPPER_LOAD1 = BitmapPainter(useResource("hopper_load1.png", ::loadImageBitmap))
    val HOPPER_LOAD2 = BitmapPainter(useResource("hopper_load2.png", ::loadImageBitmap))
    val HOPPER_JUMP = BitmapPainter(useResource("hopper_jump.png", ::loadImageBitmap))

    val DOOR_OPEN = BitmapPainter(useResource("door_open.png", ::loadImageBitmap))
    val DOOR_CLOSED = BitmapPainter(useResource("door_closed.png", ::loadImageBitmap))
    val KEY = BitmapPainter(useResource("key.png", ::loadImageBitmap))

    val LADDER_TEXTURE = BitmapPainter(useResource("ladder.png", ::loadImageBitmap))
    val VINE_TEXTURE = BitmapPainter(useResource("vine.png", ::loadImageBitmap))

    val MOSS1_TEXTURE = BitmapPainter(useResource("moss1.png", ::loadImageBitmap))
    val MOSS2_TEXTURE = BitmapPainter(useResource("moss2.png", ::loadImageBitmap))
    val MOSS3_TEXTURE = BitmapPainter(useResource("moss3.png", ::loadImageBitmap))

    val LEFT_CORNER_MOSS1 = BitmapPainter(useResource("leftCorner_moss1.png", ::loadImageBitmap))
    val RIGHT_CORNER_MOSS1 = BitmapPainter(useResource("rightCorner_moss1.png", ::loadImageBitmap))
    val LEFT_CORNER_MOSS2 = BitmapPainter(useResource("leftCorner_moss2.png", ::loadImageBitmap))
    val RIGHT_CORNER_MOSS2 = BitmapPainter(useResource("rightCorner_moss2.png", ::loadImageBitmap))
    val LEFT_CORNER_WOOD = BitmapPainter(useResource("leftCorner_wood.png", ::loadImageBitmap))
    val RIGHT_CORNER_WOOD = BitmapPainter(useResource("rightCorner_wood.png", ::loadImageBitmap))
    val HANG_LAMP = BitmapPainter(useResource("lamp.png", ::loadImageBitmap))

    val PLATFORM_TEXTURE = BitmapPainter(useResource("platform.png", ::loadImageBitmap))

    val SPIKE_TEXTURE = BitmapPainter(useResource("spikes.png", ::loadImageBitmap))

    val COIN = BitmapPainter(useResource("coin.png", ::loadImageBitmap))

    val WALL_SOLID = BitmapPainter(useResource("wall_solid.png", ::loadImageBitmap))
    val WALL_NIPPLE_BOTTOM = BitmapPainter(useResource("wall_nippleBottom.png", ::loadImageBitmap))
    val WALL_NIPPLE_LEFT = BitmapPainter(useResource("wall_nippleLeft.png", ::loadImageBitmap))
    val WALL_NIPPLE_RIGHT = BitmapPainter(useResource("wall_nippleRight.png", ::loadImageBitmap))
    val WALL_NIPPLE_TOP = BitmapPainter(useResource("wall_nippleTop.png", ::loadImageBitmap))
    val WALL_BOTTOM_LEFT = BitmapPainter(useResource("wall_bottomLeft.png", ::loadImageBitmap))
    val WALL_BOTTOM_RIGHT = BitmapPainter(useResource("wall_bottomRight.png", ::loadImageBitmap))
    val WALL_TOP_LEFT = BitmapPainter(useResource("wall_topLeft.png", ::loadImageBitmap))
    val WALL_TOP_RIGHT = BitmapPainter(useResource("wall_topRight.png", ::loadImageBitmap))
    val WALL_HORIZONTAL = BitmapPainter(useResource("wall_horizontal.png", ::loadImageBitmap))
    val WALL_VERTICAL = BitmapPainter(useResource("wall_vertical.png", ::loadImageBitmap))
    val WALL_BOTTOM = BitmapPainter(useResource("wall_bottom.png", ::loadImageBitmap))
    val WALL_LEFT = BitmapPainter(useResource("wall_left.png", ::loadImageBitmap))
    val WALL_RIGHT = BitmapPainter(useResource("wall_right.png", ::loadImageBitmap))
    val WALL_TOP = BitmapPainter(useResource("wall_top.png", ::loadImageBitmap))

    val CHAR_0 = BitmapPainter(useResource("char_0.png", ::loadImageBitmap))
    val CHAR_1 = BitmapPainter(useResource("char_1.png", ::loadImageBitmap))
    val CHAR_2 = BitmapPainter(useResource("char_2.png", ::loadImageBitmap))
    val CHAR_3 = BitmapPainter(useResource("char_3.png", ::loadImageBitmap))
    val CHAR_4 = BitmapPainter(useResource("char_4.png", ::loadImageBitmap))
    val CHAR_5 = BitmapPainter(useResource("char_5.png", ::loadImageBitmap))
    val CHAR_6 = BitmapPainter(useResource("char_6.png", ::loadImageBitmap))
    val CHAR_7 = BitmapPainter(useResource("char_7.png", ::loadImageBitmap))
    val CHAR_8 = BitmapPainter(useResource("char_8.png", ::loadImageBitmap))
    val CHAR_9 = BitmapPainter(useResource("char_9.png", ::loadImageBitmap))
    val CHAR_A = BitmapPainter(useResource("char_a.png", ::loadImageBitmap))
    val CHAR_B = BitmapPainter(useResource("char_b.png", ::loadImageBitmap))
    val CHAR_C = BitmapPainter(useResource("char_c.png", ::loadImageBitmap))
    val CHAR_D = BitmapPainter(useResource("char_d.png", ::loadImageBitmap))
    val CHAR_E = BitmapPainter(useResource("char_e.png", ::loadImageBitmap))
    val CHAR_F = BitmapPainter(useResource("char_f.png", ::loadImageBitmap))
    val CHAR_G = BitmapPainter(useResource("char_g.png", ::loadImageBitmap))
    val CHAR_H = BitmapPainter(useResource("char_h.png", ::loadImageBitmap))
    val CHAR_I = BitmapPainter(useResource("char_i.png", ::loadImageBitmap))
    val CHAR_J = BitmapPainter(useResource("char_j.png", ::loadImageBitmap))
    val CHAR_K = BitmapPainter(useResource("char_k.png", ::loadImageBitmap))
    val CHAR_L = BitmapPainter(useResource("char_l.png", ::loadImageBitmap))
    val CHAR_M = BitmapPainter(useResource("char_m.png", ::loadImageBitmap))
    val CHAR_N = BitmapPainter(useResource("char_n.png", ::loadImageBitmap))
    val CHAR_O = BitmapPainter(useResource("char_o.png", ::loadImageBitmap))
    val CHAR_P = BitmapPainter(useResource("char_p.png", ::loadImageBitmap))
    val CHAR_Q = BitmapPainter(useResource("char_q.png", ::loadImageBitmap))
    val CHAR_R = BitmapPainter(useResource("char_r.png", ::loadImageBitmap))
    val CHAR_S = BitmapPainter(useResource("char_s.png", ::loadImageBitmap))
    val CHAR_T = BitmapPainter(useResource("char_t.png", ::loadImageBitmap))
    val CHAR_U = BitmapPainter(useResource("char_u.png", ::loadImageBitmap))
    val CHAR_V = BitmapPainter(useResource("char_v.png", ::loadImageBitmap))
    val CHAR_W = BitmapPainter(useResource("char_w.png", ::loadImageBitmap))
    val CHAR_X = BitmapPainter(useResource("char_x.png", ::loadImageBitmap))
    val CHAR_Y = BitmapPainter(useResource("char_y.png", ::loadImageBitmap))
    val CHAR_Z = BitmapPainter(useResource("char_z.png", ::loadImageBitmap))
    val CHAR_BRA = BitmapPainter(useResource("char_bra.png", ::loadImageBitmap))
    val CHAR_CKET = BitmapPainter(useResource("char_cket.png", ::loadImageBitmap))
    val CHAR_SPACE = BitmapPainter(useResource("char_space.png", ::loadImageBitmap))
    val CHAR_ARROW_LEFT = BitmapPainter(useResource("char_arrow_left.png", ::loadImageBitmap))
    val CHAR_ARROW_RIGHT = BitmapPainter(useResource("char_arrow_right.png", ::loadImageBitmap))
    val CHAR_COMMA = BitmapPainter(useResource("char_comma.png", ::loadImageBitmap))
    val CHAR_DOT = BitmapPainter(useResource("char_dot.png", ::loadImageBitmap))
    val CHAR_COLON = BitmapPainter(useResource("char_colon.png", ::loadImageBitmap))

}