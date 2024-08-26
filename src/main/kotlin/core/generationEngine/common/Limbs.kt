package core.generationEngine.common

data class Limbs(
    val list: MutableList<Limb> = mutableListOf(),
    val connections: HashMap< Pair<Int, Int>, MutableSet<Pair<Int, Int>> > = hashMapOf()
)
