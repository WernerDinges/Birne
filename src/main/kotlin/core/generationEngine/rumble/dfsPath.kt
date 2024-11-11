package core.generationEngine.rumble

import core.generationEngine.EngineScope
import core.generationEngine.common.LimbPath
import core.level.TileID.WALL

/**
 * Performs a depth-first search (DFS) to determine the longest path of limbs.
 *
 * This method explores all possible paths in the game level's limb structure to find the longest connected sequence.
 * It utilizes recursive DFS to traverse the limb connections and keeps track of visited nodes and the current path.
 * Once the longest path is identified, it selects appropriate start and end points from the path.
 *
 * @return A `Triple` containing the longest identified `LimbPath`, the start coordinates as a `Pair` of integers,
 * and the end coordinates as a `Pair` of integers.
 */
fun EngineScope.dfsPath(): Triple<LimbPath, Pair<Int, Int>, Pair<Int, Int>> {
    var longest = LimbPath()

    // Finding the longest path

    fun dfs(current: Pair<Int, Int>, path: LimbPath) {
        if(path.limbs.size > longest.limbs.size)
            longest = path

        for(limb in limbs.list) {
            val nextNode =
                if(limb.start == current) limb.end
                else if(limb.end == current) limb.start
                else continue

            if(nextNode !in path.visited)
                dfs(nextNode, LimbPath(
                    path.limbs + limb,
                    path.visited + nextNode
                ))
        }
    }

    for(limb in limbs.list) {
        dfs(limb.end, LimbPath(listOf(limb), setOf(limb.start, limb.end)))
    }

    // Choosing start and finish
    val roll = mutableListOf<Pair<Int, Int>>()
    with(longest.limbs) {
        if(size > 1) {
            val first = first()
            val second = get(1)
            val prelast = get(lastIndex - 1)
            val last = last()

            roll += if (first.start != second.start && first.start != second.end) first.start else first.end
            roll += if (last.start != prelast.start && last.start != prelast.end) last.start else last.end
        } else {
            roll += mutableListOf(first().start, first().end)
        }
    }
    val rolled = roll.shuffled()

    return Triple(longest, rolled.first(), rolled.last())
}