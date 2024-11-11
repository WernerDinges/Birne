package core.entity.collectable

import core.entity.Entity

/**
 * Represents an entity that can be collected within the game.
 * This interface extends the Entity interface, enriching it with behavior specific to collectable items.
 */
interface Collectable: Entity {

    /**
     * Represents the total number of ticks that the `Collectable` entity has existed.
     * This variable is used to track the lifespan of the `Collectable` and is incremented
     * over time as the game progresses.
     */
    var ticks: Long

    /**
     * Defines the action to be taken when the collectable entity is to be removed or destroyed.
     * Typically called when a player collects the entity, or it reaches its end of life in the game.
     */
    var perish: () -> Unit

}