package core.generationEngine.rumble

import core.entity.enemy.Walker
import core.generationEngine.common.Limb
import core.level.LevelConfig

fun spawnWalker(level: LevelConfig, limb: Limb) = Walker(
    x = limb.middleX().toFloat(),
    y = limb.end.second.toFloat() - 0.0001f,
    isMirrored = limb.start.first < level.startPoint.first
)