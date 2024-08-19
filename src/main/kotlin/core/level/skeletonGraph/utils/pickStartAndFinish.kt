package core.level.skeletonGraph.utils

import core.level.skeletonGraph.SkeletonEdge
import core.level.skeletonGraph.SkeletonPath

fun pickStartAndFinish(
    longPath: List<SkeletonEdge>,
    startEdge: SkeletonEdge,
    finishEdge: SkeletonEdge
): Pair<Pair<Int, Int>, Pair<Int, Int>> {

    val last = longPath.dropLast(1).last()

    return (
        if (startEdge.start == longPath[1].start || startEdge.start == longPath[1].end)
            startEdge.end
        else
            startEdge.start
    ) to (
        if (finishEdge.start == last.start || finishEdge.start == last.end)
            finishEdge.end
        else
            finishEdge.start
    )
}