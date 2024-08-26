package core.game

import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.key.Key.Companion.A
import androidx.compose.ui.input.key.Key.Companion.D
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.input.key.Key.Companion.S
import androidx.compose.ui.input.key.Key.Companion.Spacebar
import androidx.compose.ui.input.key.Key.Companion.W
import core.entity.player.PlayerInput
import core.entity.player.PlayerSkin
import core.entity.player.PlayerState.*
import core.generationEngine.GenerationEngine
import core.generationEngine.rumble.RumbleEngine
import core.level.TileID.PLATFORM

class GameBuilder {

    private var dungeon = 0
    private var globalDifficulty = 1
    private var engine: GenerationEngine = RumbleEngine
    private var playerInput = PlayerInput(PlayerSkin.Classic)

    private var game = Game(blankGameConfig(dungeon, globalDifficulty, playerInput, engine))

    infix fun dungeon(n: Int) {
        dungeon = n
    }
    infix fun difficulty(n: Int) {
        globalDifficulty = n
    }
    infix fun engine(value: GenerationEngine) {
        engine = value
    }
    infix fun skin(value: PlayerSkin) {
        playerInput.skin = value
    }

    fun build(): Game {
        game = Game(blankGameConfig(dungeon, globalDifficulty, playerInput, engine))
            .apply { level = newLevel() }

        val xSpeed = game.level!!.player.speed
        val ySpeed = -xSpeed
        val queue = mutableListOf<Key>()

        val handleKeyEvent: (KeyEvent) -> Boolean = { keyEvent ->

            with(game.level!!.player) {
                // Climb up
                val w = {
                    queue.remove(W)
                    queue += W
                    if(isLadder) {
                        state = CLIMB
                        vy = ySpeed
                    } else if(state == CLIMB) {
                        state = IDLE
                    }
                }
                // Move left
                val a = {
                    if(state != JUMP && !isLadder)
                        state = MOVE
                    queue.remove(A)
                    queue += A
                    isMirrored = true
                    vx = -xSpeed
                }
                // Climb down
                val s = {
                    val levelTiles = game.level!!.config.tileSkeleton
                    val x = game.level!!.player.x.toInt()
                    val y = (game.level!!.player.y + 1.5f).toInt()

                    queue.remove(S)
                    queue += S

                    if(isLadder && levelTiles[y][x] != PLATFORM) {
                        state = CLIMB
                        vy = -ySpeed
                    } else if(state == CLIMB) {
                        state = IDLE
                    } else if(state == DROP) {
                        state = MOVE
                    } else {
                        state = DROP
                    }
                }
                // Move right
                val d = {
                    if(state != JUMP && !isLadder)
                        state = MOVE
                    queue.remove(D)
                    queue += D
                    isMirrored = false
                    vx = xSpeed
                }
                // Jump
                val spacebar = {
                    if(state != JUMP) {
                        state = JUMP
                        vy = -jumpStrength
                    } else if(doubleJumpLeft == 0L) {
                        doubleJumpLeft = doubleJumpCooldown
                        state = JUMP
                        vy = -jumpStrength
                    }
                }
                val r = {
                    if(hp > 1) {
                        queue.remove(R)
                        queue += R
                        hp--
                        x = game.level!!.config.startPoint.first.toFloat()
                        y = game.level!!.config.startPoint.second.toFloat() - 0.05f

                        state = IDLE
                    }
                }

                when(keyEvent.type) {

                    KeyEventType.KeyDown -> {
                        when(keyEvent.key) {
                            W -> w()
                            A -> a()
                            S -> s()
                            D -> d()
                            R -> r()
                            Spacebar -> spacebar()
                        }
                    }

                    KeyEventType.KeyUp -> {
                        val removed = queue.remove(keyEvent.key)

                        if(removed) {
                            vx = 0f
                            if(keyEvent.key in setOf(W, S)) vy = 0f

                            when (queue.lastOrNull()) {
                                W -> w()
                                A -> a()
                                S -> s()
                                D -> d()
                            }
                        }
                    }

                }
            }

            true
        }

        game.handleKeyEvent = handleKeyEvent
        return game
    }

    private fun blankGameConfig(
        dungeon: Int,
        difficulty: Int, playerInput: PlayerInput,
        engine: GenerationEngine
    ): GameConfig {
        return GameConfig(dungeon, difficulty, 0, playerInput, engine)
    }

}