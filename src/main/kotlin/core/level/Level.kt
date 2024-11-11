package core.level

import Birne
import Birne.cellSize
import Textures.HEART
import Textures.HEART_EMPTY
import Textures.JUMP
import Textures.JUMP_EMPTY
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.BitmapPainter
import core.level.TileID.FINISH_OPEN
import core.level.TileID.LADDER
import core.level.TileID.PLATFORM
import core.level.TileID.SPIKE
import core.level.TileID.VINE
import core.level.TileID.WALL
import core.entity.player.Player
import core.entity.player.PlayerInput
import core.entity.player.PlayerState
import core.game.GameConfig
import utils.drawText
import utils.sizeOfCell
import kotlin.math.ceil
import kotlin.math.floor

/**
 * Represents a level in the game containing configuration and various game entities.
 *
 * @property config The configuration details specific to this level.
 * @property player The main player entity of the level.
 * @property coins A lazy property that yields the number of coins in the game configuration.
 * @property backgroundTiles A 2D array that stores the background tile images.
 */
class Level(
    val config: LevelConfig,
    gameConfig: GameConfig,
    playerInput: PlayerInput
) {
    /**
     * Represents the player character in the game level.
     *
     * The player can move, jump, and interact with the game environment.
     * Player state is managed by updating its position, velocity, and other attributes.
     *
     * See [Player] for detailed attributes and behavior of the player character.
     */
    val player: Player
    /**
     * A lambda function that retrieves the current number of coins available to the player
     * in the game from the [gameConfig].
     *
     * @return The number of coins available to the player.
     */
    val coins = { gameConfig.coins }
    /**
     * A two-dimensional array representing the background tiles of the game level.
     * Each element in the array is a nullable `BitmapPainter` object, which can be used
     * to draw background images in the game.
     *
     * This array is utilized in the `drawMap()` function within the `DrawScope` to render
     * the game environment based on the configured tiles.
     */
    val backgroundTiles: Array<Array<BitmapPainter?>>

    init {
        gameConfig.engine.launch(config, gameConfig)

        backgroundTiles = getBitmapMatrix(config.tileSkeleton)

        player = Player(
            config.startPoint.first.toFloat(),
            config.startPoint.second.toFloat() - 0.01f,
            playerInput
        )
    }

    /**
     * Updates the entities in the current level, including the player and NPCs.
     *
     * @param millis The elapsed time in milliseconds since the last update.
     */
    fun updateEntities(millis: Long) {
        updatePlayer(millis)
        updateNPCs(millis)
    }

    /**
     * Updates the player's position and state based on the elapsed time, accounting for collisions
     * with the game's environment such as walls, ladders, spikes, enemies, and collectables,
     * as well as transitioning to the next level when reaching a finish tile.
     *
     * @param millis The elapsed time in milliseconds since the last update.
     */
    private fun updatePlayer(millis: Long) {
        val gap = player.hitboxOffset
        val hitbox = player.hitbox
        val map = config.tileSkeleton

        val (dx, dy) = Offset(player.vx, player.vy) * (millis.toFloat() / 1000f)

        val (topLeftX, topLeftY) = player.x+gap.x to player.y+gap.y
        val (topRightX, topRightY) = player.x+gap.x+hitbox.width to player.y+gap.y
        val (bottomLeftX, bottomLeftY) = player.x+gap.x to player.y+gap.y+hitbox.height
        val (bottomRightX, bottomRightY) = player.x+gap.x+hitbox.width to player.y+gap.y+hitbox.height

        val bottomLeft_dx = map[bottomLeftY.toInt()][(bottomLeftX+dx).toInt()]
        val bottomLeft_dy = map[(bottomLeftY+dy).toInt()][bottomLeftX.toInt()]
        val bottomLeft = map[bottomLeftY.toInt()][bottomLeftX.toInt()]
        val bottomRight_dx = map[bottomRightY.toInt()][(bottomRightX+dx).toInt()]
        val bottomRight_dy = map[(bottomRightY+dy).toInt()][bottomRightX.toInt()]
        val bottomRight = map[bottomRightY.toInt()][bottomRightX.toInt()]
        val topLeft_dx = map[topLeftY.toInt()][(topLeftX+dx).toInt()]
        val topLeft_dy = map[(topLeftY+dy).toInt()][topLeftX.toInt()]
        val topLeft = map[topLeftY.toInt()][topLeftX.toInt()]
        val topRight_dx = map[topRightY.toInt()][(topRightX+dx).toInt()]
        val topRight_dy = map[(topRightY+dy).toInt()][topRightX.toInt()]
        val topRight = map[topRightY.toInt()][topRightX.toInt()]

        val platformStep =
            ((bottomLeft_dy == PLATFORM && bottomLeft != PLATFORM && dy > 0) ||
            (bottomRight_dy == PLATFORM && bottomRight != PLATFORM && dy > 0)) &&
            player.state != PlayerState.DROP

        // Player hit a wall
        if(topLeft_dy != WALL && topRight_dy != WALL &&
                bottomLeft_dy != WALL && bottomRight_dy != WALL && !platformStep)
            player.y += dy

        else
            player.y =
                if(dy > 0)
                    ((player.y + gap.y + hitbox.height + dy)
                        .coerceAtMost(floor(bottomLeftY + dy) - 0.01f) - gap.y - hitbox.height)
                        .also {
                            player.state = if (player.vx == 0f)
                                PlayerState.IDLE
                            else
                                PlayerState.MOVE
                        }
                else if(dy < 0)
                    (player.y+gap.y+dy).coerceAtLeast(ceil(topLeftY + dy) + 0.01f) - gap.y
                else
                    player.y


        if(topLeft_dx != WALL && topRight_dx != WALL &&
                bottomLeft_dx != WALL && bottomRight_dx != WALL) {
            player.x += dx
        } else {
            player.x =
                if(dx > 0)
                    (player.x+gap.x+hitbox.width+dx).coerceAtMost(floor(bottomRightX + dx) - 0.01f) - gap.x - hitbox.width
                else if(dx < 0)
                    (player.x+gap.x+dx).coerceAtLeast(ceil(bottomLeftX + dx) + 0.01f) - gap.x
                else
                    player.x
        }

        // Player gets on a ladder tile
        if(
            topLeft in setOf(LADDER, VINE) || topRight in setOf(LADDER, VINE) ||
            bottomLeft in setOf(LADDER, VINE) || bottomRight in setOf(LADDER, VINE)
        ) {
            player.isLadder = true
        } else {
            player.isLadder = false
            if(player.state == PlayerState.CLIMB)
                player.state = PlayerState.IDLE
        }

        // Player jumps on a spike
        if((bottomLeft == SPIKE && topLeft == SPIKE) || (bottomRight == SPIKE && topRight == SPIKE)) {
            player.hp--
            player.x = config.startPoint.first.toFloat()
            player.y = config.startPoint.second.toFloat() - 0.01f
        }

        // Player hits an enemy
        for(npc in config.notPlayableEntities.filter { it.collision }) {
            val leftInX = bottomLeftX in (npc.x + npc.hitboxOffset.x)..(npc.x + npc.hitboxOffset.x + npc.hitbox.width)
            val leftInY = bottomLeftY in (npc.y + npc.hitboxOffset.y)..(npc.y + npc.hitboxOffset.y + npc.hitbox.height)
            val rightInX = bottomRightX in (npc.x + npc.hitboxOffset.x)..(npc.x + npc.hitboxOffset.x + npc.hitbox.width)
            val rightInY = bottomRightY in (npc.y + npc.hitboxOffset.y)..(npc.y + npc.hitboxOffset.y + npc.hitbox.height)

            if((leftInX && leftInY) || (rightInX && rightInY)) {
                player.hurt(config)
            }
        }

        // Player collects a collectable
        for(col in config.collectables) {
            val leftInX = bottomLeftX in (col.x + col.hitboxOffset.x)..(col.x + col.hitboxOffset.x + col.hitbox.width)
            val leftInY = bottomLeftY in (col.y + col.hitboxOffset.y)..(col.y + col.hitboxOffset.y + col.hitbox.height)
            val rightInX = bottomRightX in (col.x + col.hitboxOffset.x)..(col.x + col.hitboxOffset.x + col.hitbox.width)
            val rightInY = bottomRightY in (col.y + col.hitboxOffset.y)..(col.y + col.hitboxOffset.y + col.hitbox.height)

            if((leftInX && leftInY) || (rightInX && rightInY)) {
                col.perish()
            }
        }

        // Player gets on a door tile
        if(bottomLeft == FINISH_OPEN || bottomRight == FINISH_OPEN)
            Birne.nextLevel()

    }

    /**
     * Updates the state of all non-playable characters (NPCs) based on the elapsed time.
     *
     * @param millis The time in milliseconds since the last update cycle.
     */
    private fun updateNPCs(millis: Long) {
        for(npc in config.notPlayableEntities)
            npc.thinkAndAct(millis, config.tileSkeleton, config, player)
    }

    /**
     * Draws the entire game map on the screen.
     *
     * This function handles the rendering of background tiles, non-playable entities,
     * collectable items, and the player character. It calculates the appropriate
     * scaling and translation based on the map size and offsets to center the map
     * on the screen.
     */
    fun DrawScope.drawMap() {
        val (w, h) = size.width to size.height
        cellSize = h/(config.mapSize.second+1)
        val offsetX = (w - config.mapSize.first*cellSize)/2f
        val offsetY = (h - config.mapSize.second*cellSize)/2f

        for(y in backgroundTiles.indices)
            for(x in backgroundTiles[0].indices)
                if(backgroundTiles[y][x] != null)
                    with(backgroundTiles[y][x]!!) {
                        translate(
                            left = offsetX + x*cellSize,
                            top = offsetY + y*cellSize
                        ) {
                            draw(sizeOfCell())
                        }
                    }

        for(entity in config.notPlayableEntities)
            with(entity) {
                draw(w, offsetX, offsetY)
            }

        for(collectable in config.collectables)
            with(collectable) {
                draw(w, offsetX, offsetY)
            }

        with(player) {
            draw(w, offsetX, offsetY)
        }
    }

    /**
     * Draws the UI elements for the game level, including the player's health points, double-jump cooldown,
     * the number of coins collected, and the level number.
     *
     * The method renders the following UI components:
     * - Player's health points as hearts, displaying filled or empty hearts based on the player's current health.
     * - Double-jump cooldown as icons, showing the remaining cooldown time in seconds.
     * - The number of coins collected by the player.
     * - The current level or room number.
     *
     * This method should be called within a drawing scope to ensure the UI elements are rendered correctly
     * on the game screen.
     */
    fun DrawScope.drawUI() {
        // Health points
        for(i in 1..player.maxHp)
            with(if(i > player.hp) HEART_EMPTY else HEART) {
                translate(
                    left = (i-1)*cellSize,
                    top = size.height - cellSize
                ) {
                    draw(sizeOfCell())
                }
            }

        // Double-jump cooldown
        for(i in 1..(player.doubleJumpCooldown/1000).toInt())
            with(if(i <= ceil(player.doubleJumpLeft/1000f)) JUMP_EMPTY else JUMP) {
                translate(
                    left = size.width - cellSize*(player.doubleJumpCooldown/1000).toInt() + cellSize*(i-1),
                    top = size.height - cellSize
                ) {
                    draw(sizeOfCell())
                }
            }

        // Coins
        drawText(
            text = "${coins()}",
            left = { i -> size.width - cellSize/2f * ("${coins()}".length + 1) + i * cellSize/2f },
            top = { cellSize/2f }
        )

        // Level number
        drawText(
            text = "ROOM ${config.number}",
            left = { i -> i * cellSize / 2f + 16f },
            top = { 16f }
        )

    }
}