package utils

import Birne
import androidx.compose.runtime.*
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.key.Key.Companion.A
import androidx.compose.ui.input.key.Key.Companion.D
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.input.key.Key.Companion.S
import androidx.compose.ui.input.key.Key.Companion.Spacebar
import androidx.compose.ui.input.key.Key.Companion.W
import core.game.Game
import core.entity.player.PlayerState.*
import core.level.Level
import kotlinx.coroutines.*

@Composable
fun initNewGame(game: Game) {
    val xSpeed = (game.state as Level).player.speed
    val ySpeed = -xSpeed
    var job: Job? = null

    val queue = mutableListOf<Key>()

    DisposableEffect(Unit) {
        val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

        onDispose {
            job?.cancel()
            coroutineScope.cancel()
        }

        val handleKeyEvent: (KeyEvent) -> Boolean = { keyEvent ->

            with((game.state as Level).player) {
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
                    queue.remove(S)
                    queue += S
                    if(isLadder) {
                        state = CLIMB
                        vy = -ySpeed
                    } else if(state in setOf(CLIMB, DROP)) {
                        state = IDLE
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
                    if((game.state as Level).player.hp > 1) {
                        queue.remove(R)
                        queue += R
                        (game.state as Level).player.hp--
                        (game.state as Level).player.x = (game.state as Level).startPoint.first.toFloat()
                        (game.state as Level).player.y = (game.state as Level).startPoint.second.toFloat()
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

        onDispose { }
    }
}