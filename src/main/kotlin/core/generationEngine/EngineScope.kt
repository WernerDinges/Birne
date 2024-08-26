package core.generationEngine

import core.game.GameConfig
import core.generationEngine.common.*
import core.level.LevelConfig
import core.level.TileID.FINISH_CLOSED
import core.level.TileID.FINISH_OPEN
import core.level.TileID.START

class EngineScope(
    var level: LevelConfig,
    var game: GameConfig
) {
    var decoratables = Decoratable()
    var limbs = Limbs()
    var longestPath = LimbPath()

    fun limbs(scope: EngineScope.() -> Limbs) {
        limbs = scope()
    }

    fun longestPath(scope: EngineScope.() -> Triple<LimbPath, Pair<Int, Int>, Pair<Int, Int>>) {
        val (longPath, startAt, endAt) = scope()
        longestPath = longPath
        level.startPoint = startAt
        level.endPoint = endAt
    }

    fun entities(scope: EntityScope.() -> Unit) {
        EntityScope(level, game, longestPath).apply(scope)
    }

    fun collectables(scope: CollectableScope.() -> Unit) {
        CollectableScope(level, game, longestPath, limbs).apply(scope)
    }

    fun drawStartAndFinish() {
        level.tileSkeleton[level.startPoint.second][level.startPoint.first] = START
        level.tileSkeleton[level.endPoint.second][level.endPoint.first] = FINISH_OPEN
            //if(level.doorClosed) FINISH_CLOSED else FINISH_OPEN
    }
}