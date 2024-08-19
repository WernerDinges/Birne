package core.level.skeletonGraph.utils

fun sliceLevelMap(size: Int) = mutableListOf<Int>().apply {
    var point = 1
    while(point < size-3) {
        this += point
        point += (3..4).shuffled()[0]
    }
    if(this.last() < size-3)
        this += size-2
}.sorted()