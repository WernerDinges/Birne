package core.level.skeletonGraph

import core.entity.collectable.Collectable
import core.entity.enemy.NotPlayable
import core.game.Game
import core.level.Level
import core.level.skeletonGraph.LevelSkeleton.AIR
import core.level.skeletonGraph.LevelSkeleton.FINISH_CLOSED
import core.level.skeletonGraph.LevelSkeleton.FINISH_OPEN
import core.level.skeletonGraph.LevelSkeleton.LADDER
import core.level.skeletonGraph.LevelSkeleton.PLATFORM
import core.level.skeletonGraph.LevelSkeleton.START
import core.level.skeletonGraph.LevelSkeleton.VINE
import core.level.skeletonGraph.LevelSkeleton.WALL
import core.level.skeletonGraph.utils.*
import kotlin.random.Random.Default.nextBoolean
import kotlin.random.Random.Default.nextInt

fun generateLevelSkeleton(
    size: Pair<Int, Int>, difficulty: Int,
    tileSkeleton: (Array<Array<Int>>) -> Unit,
    startPoint: (Pair<Int, Int>) -> Unit,
    notPlayableEntities: (List<NotPlayable>) -> Unit,
    collectables: (List<Collectable>) -> Unit,
    removeFromCollectables: (x: Float, y: Float) -> Unit,
    addCoins: (Int) -> Unit
) {

    val entities = mutableListOf<NotPlayable>()
    var levelCollectables = mutableListOf<Collectable>()

    val horizontals = sliceLevelMap(size.first)
    val verticals = sliceLevelMap(size.second)

    val limbs = generateLimbs(verticals, horizontals)

    // Level skeleton itself
    var primordial = blankPrimordial(size, limbs)

    // Creating hollow spaces
    drillHoles(primordial, size)

    val longPath = longestPath(limbs).edges

    // Picking spawn and the door's positions
    val (startEdge, finishEdge) = listOf(longPath.first(), longPath.last()).shuffled()
    val (start, finish) = pickStartAndFinish(longPath, startEdge, finishEdge)
    primordial[start.second][start.first] = START
    primordial[finish.second][finish.first] = FINISH_OPEN

    var linearEnemyWeights = generateLinEnemyWeights(longPath, primordial, difficulty)
    
    // Vertical paths analyzer
    var verticalStreak = 0
    // Filling the main path
    for(i in longPath.indices)

        // Vertical
        if(longPath[i].isVertical()) {
            verticalStreak++

            if(i + 1 <= longPath.lastIndex)
                if(longPath[i+1].isVertical()) continue

            // Tips to the streak
            val (minY, maxY) = with(pathPair(longPath, i, verticalStreak)) { min() to max() }

            // Ladder 50%
            if(nextBoolean())
                for(y in (minY + 1)..maxY)
                    primordial[y][longPath[i].start.first] = LADDER
            // Vine 50%
            else {
                var y = maxY
                do {
                    primordial[y][longPath[i].start.first] = VINE
                    y--
                } while (primordial[y][longPath[i].start.first] !in setOf(WALL, PLATFORM))
            }
        }

        // Horizontal
        else {
            verticalStreak = 0

            // Tips of the limb
            val (minX, maxX) = with(
                setOf(longPath[i].start.first, longPath[i].end.first)
            ) { min() to max() }

            val levelY = longPath[i].end.second

            for(x in minX..maxX) {
                val noFloor = primordial[levelY + 1][x] != WALL
                val spawnOrFinish = primordial[levelY][x] in setOf(START, FINISH_OPEN, FINISH_CLOSED)
                val chance1of2 = nextBoolean()
                val holeLeft = primordial[levelY + 1][x - 1] == AIR
                val holeRight = primordial[levelY + 1][x + 1] == AIR

                if(noFloor && (spawnOrFinish || chance1of2 || (holeLeft && holeRight)))
                    primordial[levelY + 1][x] = PLATFORM
            }

            val weightChance = (nextInt(100) + 1) in 0..(linearEnemyWeights.lastOrNull() ?: 0)
            if(isSafeToGenerateEnemy(primordial, longPath, longPath[i])) {
                if (weightChance)
                    entities += spawnWalker(minX, maxX, levelY, start.first)
                linearEnemyWeights = linearEnemyWeights.dropLast(1)
            }
        }

    putDecorations(primordial)

    generateSpikes(primordial, limbs, difficulty)

    putTreasures(
        primordial, limbs, longPath,
        levelCollectables, removeFromCollectables,
        addCoins
    )

    primordial[start.second][start.first] = START
    primordial[finish.second][finish.first] = FINISH_OPEN

    tryToCloseDoor(
        finish, primordial,
        limbs.filter { (_, exists) -> exists }.keys.toList(),
        levelCollectables, removeFromCollectables
    )

    tileSkeleton(primordial)
    startPoint(start)
    notPlayableEntities(entities)
    collectables(levelCollectables)
}