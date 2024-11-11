package core.generationEngine

import core.game.GameConfig
import core.generationEngine.common.*
import core.level.LevelConfig
import core.level.TileID.FINISH_CLOSED
import core.level.TileID.FINISH_OPEN
import core.level.TileID.START

/**
 * DSL element providing configuration for levels, game settings,
 * and various game components like limbs, paths, and entities.
 *
 * @property level The configuration settings for the current level.
 * @property game The configuration settings for the game.
 */
class EngineScope(
    var level: LevelConfig,
    var game: GameConfig
) {
    /**
     * A variable for holding a collection of decoratable elements within the game engine.
     *
     * This property stores instances of `Decoratable`, which typically contain information
     * on various elements that can be decorated, such as positions where specific
     * decorations might be applied within the level's structure.
     */
    var decoratables = Decoratable()
    /**
     * Represents a collection of limbs within the engine scope.
     * The limbs variable is used to manage and manipulate a list of limb connections
     * in the game, facilitating various game mechanics like drawing paths,
     * placing obstacles, or configuring collectables.
     */
    var limbs = Limbs()
    /**
     * The longest path of limbs within the game configuration.
     * It represents a sequence of connected segments (limbs) traversed in the game level.
     *
     * This variable is used in various scopes to affect game elements like entities,
     * platforms, ladders, and collectables, based on the longest identified path in the level.
     */
    var longestPath = LimbPath()

    /**
     * Configures the `limbs` property of the `EngineScope` by applying the provided
     * configuration function to the `EngineScope` instance and assigning the result to `limbs`.
     *
     * @param scope A lambda with receiver of type `EngineScope` that returns a `Limbs` object.
     *              This lambda is used to configure the `limbs` property.
     */
    fun limbs(scope: EngineScope.() -> Limbs) {
        limbs = scope()
    }

    /**
     * Sets the longest path configuration for the engine scope. This method is used to determine and assign
     * the longest path within a level, along with the starting and ending points of the path.
     *
     * @param scope A lambda function that provides a Triple consisting of a `LimbPath` object representing the
     *              longest path, a Pair of integers representing the starting point coordinates, and another
     *              Pair of integers representing the ending point coordinates.
     */
    fun longestPath(scope: EngineScope.() -> Triple<LimbPath, Pair<Int, Int>, Pair<Int, Int>>) {
        val (longPath, startAt, endAt) = scope()
        longestPath = longPath
        level.startPoint = startAt
        level.endPoint = endAt
    }

    /**
     * Configures the entities for the current game level based on the provided scope.
     * Utilizes the `EntityScope` to define various entities to be placed in the level.
     *
     * @param scope A lambda receiver of type `EntityScope` that contains the logic for entity placement and configuration.
     */
    fun entities(scope: EntityScope.() -> Unit) {
        EntityScope(level, game, longestPath).apply(scope)
    }

    /**
     * Defines a scope for creating collectable items within a specific level configuration.
     *
     * @param scope A lambda with `CollectableScope` receiver that allows the configuration
     * of collectable items.
     */
    fun collectables(scope: CollectableScope.() -> Unit) {
        CollectableScope(level, game, longestPath, limbs).apply(scope)
    }

    /**
     * Draws the start and finish points on the level's tile skeleton.
     *
     * This function sets a start marker at the level's defined start point and a finish
     * marker at the level's defined end point. The finish marker is set as an open state by default.
     *
     * The specific markers used are indicated by the constants START and FINISH_OPEN,
     * which should be defined elsewhere in the code.
     */
    fun drawStartAndFinish() {
        level.tileSkeleton[level.startPoint.second][level.startPoint.first] = START
        level.tileSkeleton[level.endPoint.second][level.endPoint.first] = FINISH_OPEN
            //if(level.doorClosed) FINISH_CLOSED else FINISH_OPEN
    }
}