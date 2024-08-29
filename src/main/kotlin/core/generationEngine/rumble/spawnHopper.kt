package core.generationEngine.rumble

import core.entity.notPlayable.Hopper
import core.generationEngine.common.Limb
import core.level.LevelConfig

fun spawnHopper(level: LevelConfig, limb: Limb) = Hopper(
    x = limb.middleX().toFloat(),
    y = limb.end.second.toFloat() - 0.0001f,
    isMirrored = limb.start.first < level.startPoint.first
)