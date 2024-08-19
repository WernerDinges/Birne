package core.entity.collectable

import core.entity.Entity

interface Collectable: Entity {

    var ticks: Long

    var perish: () -> Unit

}