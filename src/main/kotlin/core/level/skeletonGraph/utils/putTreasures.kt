package core.level.skeletonGraph.utils

import core.entity.collectable.Coin
import core.entity.collectable.Collectable
import core.level.skeletonGraph.LevelSkeleton.FINISH_CLOSED
import core.level.skeletonGraph.LevelSkeleton.FINISH_OPEN
import core.level.skeletonGraph.LevelSkeleton.SPIKE
import core.level.skeletonGraph.LevelSkeleton.START
import core.level.skeletonGraph.LevelSkeleton.WALL
import core.level.skeletonGraph.SkeletonEdge
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random.Default.nextInt

fun putTreasures(
    primordial: Array<Array<Int>>,
    limbs: Map<SkeletonEdge, Boolean>,
    longPath: List<SkeletonEdge>,
    collectables: MutableList<Collectable>,
    removeFromCollectables: (x: Float, y: Float) -> Unit,
    addCoins: (Int) -> Unit
) {
    val prohibited = setOf(WALL, FINISH_OPEN, FINISH_CLOSED, SPIKE, START)
    val notMainPathLimbs = limbs.filter { (edge, exists) -> exists && edge !in longPath }.keys

    limb@for(limb in notMainPathLimbs) {
        val coinChance = 10
        val containerChance = 3
        val roll = nextInt(100)

        if(!limb.isVertical()) {
            var (x, y) = 0f to 0f
            val tryY = limb.start.second
            var spawn = false
            chooseX@for(tryX in (min(limb.start.first, limb.end.first)..max(limb.start.first, limb.end.first)).shuffled()) {
                if(primordial[tryY][tryX] !in prohibited) {
                    y = tryY.toFloat()
                    x = tryX.toFloat()
                    spawn = true
                    break@chooseX
                } else if(primordial[tryY-1][tryX] !in prohibited) {
                    y = (tryY-1).toFloat()
                    x = tryX.toFloat()
                    spawn = true
                    break@chooseX
                }
            }

            if(spawn)
                when(roll) {
                    // Spawn a container
                    in 0 ..< containerChance -> {
                        // TODO
                    }
                    // Spawn a coin
                    in containerChance ..< coinChance -> {
                        collectables += Coin(x, y) {
                            removeFromCollectables(x, y)
                            addCoins(1)
                        }
                    }
                }
        }
    }

}