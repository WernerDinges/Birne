package core.level

import kotlin.random.Random.Default.nextInt

/**
 * Determines the dimensions of the game level based on the provided difficulty setting.
 *
 * @param difficulty The difficulty setting for the level; this influences its dimensions.
 * @return A pair containing the width and height of the level.
 */
fun getLevelSize(difficulty: Int): Pair<Int, Int> = when(difficulty) {
    1 -> nextInt(9, 12) to nextInt(9, 12)
    2 -> nextInt(10, 13) to nextInt(10, 13)
    3 -> nextInt(11, 14) to nextInt(11, 14)
    4 -> nextInt(12, 15) to nextInt(12, 15)
    5 -> nextInt(13, 16) to nextInt(13, 16)
    6 -> nextInt(14, 17) to nextInt(14, 17)

    else -> 18 to 18
}