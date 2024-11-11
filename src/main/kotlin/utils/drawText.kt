package utils

import Textures.CHAR_0
import Textures.CHAR_1
import Textures.CHAR_2
import Textures.CHAR_3
import Textures.CHAR_4
import Textures.CHAR_5
import Textures.CHAR_6
import Textures.CHAR_7
import Textures.CHAR_8
import Textures.CHAR_9
import Textures.CHAR_A
import Textures.CHAR_ARROW_LEFT
import Textures.CHAR_ARROW_RIGHT
import Textures.CHAR_B
import Textures.CHAR_BRA
import Textures.CHAR_C
import Textures.CHAR_CKET
import Textures.CHAR_COLON
import Textures.CHAR_COMMA
import Textures.CHAR_D
import Textures.CHAR_DOT
import Textures.CHAR_E
import Textures.CHAR_F
import Textures.CHAR_G
import Textures.CHAR_H
import Textures.CHAR_I
import Textures.CHAR_J
import Textures.CHAR_K
import Textures.CHAR_L
import Textures.CHAR_M
import Textures.CHAR_N
import Textures.CHAR_O
import Textures.CHAR_P
import Textures.CHAR_Q
import Textures.CHAR_R
import Textures.CHAR_S
import Textures.CHAR_SPACE
import Textures.CHAR_T
import Textures.CHAR_U
import Textures.CHAR_V
import Textures.CHAR_W
import Textures.CHAR_X
import Textures.CHAR_Y
import Textures.CHAR_Z
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate

/**
 * Draws the given text string on a given draw scope.
 * Each character will be drawn at a specified position determined by the left and top lambdas.
 *
 * @param text The string of text to be drawn.
 * @param left Lambda function that calculates the left position for each character based on its index.
 * @param top Lambda function that calculates the top position for each character based on its index.
 */
fun DrawScope.drawText(
    text: String,
    left: (Int) -> Float,
    top: (Int) -> Float
) {
    for(i in text.indices) {
        with(charToTexture(text[i])) {
            translate(left(i), top(i)) {
                draw(sizeOfCell()/2f)
            }
        }
    }
}

private fun charToTexture(char: Char) = when(char) {
    '0' -> CHAR_0
    '1' -> CHAR_1
    '2' -> CHAR_2
    '3' -> CHAR_3
    '4' -> CHAR_4
    '5' -> CHAR_5
    '6' -> CHAR_6
    '7' -> CHAR_7
    '8' -> CHAR_8
    '9' -> CHAR_9
    'A' -> CHAR_A
    'B' -> CHAR_B
    'C' -> CHAR_C
    'D' -> CHAR_D
    'E' -> CHAR_E
    'F' -> CHAR_F
    'G' -> CHAR_G
    'H' -> CHAR_H
    'I' -> CHAR_I
    'J' -> CHAR_J
    'K' -> CHAR_K
    'L' -> CHAR_L
    'M' -> CHAR_M
    'N' -> CHAR_N
    'O' -> CHAR_O
    'P' -> CHAR_P
    'Q' -> CHAR_Q
    'R' -> CHAR_R
    'S' -> CHAR_S
    'T' -> CHAR_T
    'U' -> CHAR_U
    'V' -> CHAR_V
    'W' -> CHAR_W
    'X' -> CHAR_X
    'Y' -> CHAR_Y
    'Z' -> CHAR_Z
    '[' -> CHAR_BRA
    ']' -> CHAR_CKET
    '<' -> CHAR_ARROW_LEFT
    '>' -> CHAR_ARROW_RIGHT
    ',' -> CHAR_COMMA
    '.' -> CHAR_DOT
    ':' -> CHAR_COLON
    ' ' -> CHAR_SPACE

    else -> CHAR_SPACE // default to 0 if character is not recognized
}