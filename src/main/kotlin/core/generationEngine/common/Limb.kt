package core.generationEngine.common

data class Limb(
    val start: Pair<Int, Int>,
    val end: Pair<Int, Int>,
    val isVertical: Boolean,
    var canContainDangers: Boolean
) {

    fun middleX(): Int = (start.first..end.first).average().toInt()

    fun middleY(): Int = (start.second..end.second).average().toInt()

}
