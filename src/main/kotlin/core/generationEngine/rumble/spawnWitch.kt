package core.generationEngine.rumble

import core.entity.notPlayable.Witch
import core.generationEngine.common.EntityScope
import core.generationEngine.common.Limb
import core.level.LevelConfig

fun EntityScope.spawnWitch(level: LevelConfig, limb: Limb) = Witch(
    scope = this,
    x = limb.middleX().toFloat(),
    y = limb.end.second.toFloat() - 0.0001f,
    isMirrored = limb.start.first < level.startPoint.first
)