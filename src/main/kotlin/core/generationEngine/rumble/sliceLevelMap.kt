package core.generationEngine.rumble

fun sliceLevelMap(size: Int, start: Int) = mutableListOf<Int>().apply {
    var point = start
    while(point < size-3) {
        this += point
        point += setOf(3, 4).random()
    }
    if(this.last() < size-3)
        this += size-2
}.sorted()