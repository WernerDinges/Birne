package core.generationEngine.common

data class LimbPath(
    val limbs: List<Limb> = listOf(),
    val visited: Set<Pair<Int, Int>> = setOf()
)
