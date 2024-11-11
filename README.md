# Birne

**Birne** (German for "Pear") is a minimalistic 2D-platformer built using Kotlin + Compose for Desktop framework.
In this open-source adventure, you guide a cute, adventurous pear through procedurally generated dungeons, dodging enemies and collecting coins.
Birne is a creative playground for experimenting with procedural generation, DSLs, and modular game design in Kotlin.

![birnedemo](https://github.com/user-attachments/assets/9b3c52c5-1cc8-498b-8e02-05e5fdfe92a5)

## What are you interested in?

- [I want to play it](#i-want-to-play-it)
- [I'm a developer and I want to check out the level generation code](#procedural-generation)
- [I'm an employer and I want to check out how you organized the code for your mind-blowing project](#kotlin-dsl-for-modular-design)

- [I want to donate you](https://www.donationalerts.com/r/wernerdinges)

## About the Game

Birne follows a pear with big dreams to collect all the coin in the world to start his own farming business!
But first, he’ll need to explore dungeons filled with dangers and treasures. Along the way, players can collect
coins to unlock new skins and levels, meeting a variety of enemies with each progression.

## I want to play it

You can already download the game in [Releases](https://github.com/WernerDinges/Birne/releases) page!
Unzip the package and launch EXE to play.

- **Explore Procedurally Generated Rooms**: Each dungeon room is a unique challenge populated with obstacles, decorations, and monsters.
- **Collect and Customize**: Gather coins, unlock new levels, and purchase skins to customize your character.
- **Face New Challenges**: Each level introduces a new monster, keeping gameplay fresh and exciting.

Control in the game is simple: the mouse is not involved, the game can be opened in a small window in the corner
of the screen while you are watching a boring lecture on linear algebra.
- *A* and *D* - walk left and right with the little pear legs
- *W* and *S* - climb ladders and vines
- *Space* - jump
- *Space* x2 - double jump (it takes a couple seconds to reload)
- *R* - Lose a life and respawn

## Procedural Generation

All the code for procedural generation you can see in the game's default [generation engine](https://github.com/WernerDinges/Birne/blob/master/src/main/kotlin/core/generationEngine/rumble/RumbleEngine.kt).
Rooms and obstacles in **Birne** are generated using efficient procedural generation techniques. Here’s a brief overview of the process:

First, the grid of dots is connected randomly, giving a little more chance for horizontal tunnels to appear.
```kotlin
// Horizontal limb (to the right)
if(i < hors.lastIndex && Random.nextDouble() <= .75) {
    val rightNode = hors[i+1] to vers[j]
    val limb = Limb(currentNode, rightNode, isVertical = false, false)
    limbs.list += limb
    limbs.connections.getOrPut(currentNode) { mutableSetOf() } += rightNode
    limbs.connections.getOrPut(rightNode) { mutableSetOf() } += currentNode

    // Filling tiles with air.
    for(x in currentNode.first..rightNode.first) {
        level.tileSkeleton[currentNode.second][x] = AIR

        // Tunnel expansion in height.
        if(level.tileSkeleton[currentNode.second-1][x] == WALL && level.tileSkeleton[currentNode.second-2][x] == WALL) {
            level.tileSkeleton[currentNode.second-1][x] = AIR
            // Adding the ceiling tile to decoratable tiles.
            decoratables.hollow += Pair(x, currentNode.second-1)
        }
    }
}
```
[Source](https://github.com/WernerDinges/Birne/blob/master/src/main/kotlin/core/generationEngine/rumble/generateLimbs.kt)

We then add interest to our tunnels by filling random arrays of tiles between vertical edges with air.
```kotlin
    val groupedVerticals = limbs.list
        .filter { it.isVertical }
        .groupBy { it.start.second }

    for((_, floor) in groupedVerticals)
        for((left, right) in floor.windowed(size = 2, step = 1))
            if(right.start.first-left.start.first < 4 && Random.nextBoolean()) {

                // Drilling a hole
                for(x in left.start.first..right.start.first)
                for(y in left.start.second..left.end.second) {
                    level.tileSkeleton[y][x] = AIR
                    // Adding to decoratables
                    decoratables.hollow += Pair(x, y)
                }

            }

    for(limb in limbs.list) limb.apply {
        canContainDangers = (
            // The tunnel is wide enough and has floor
            level.tileSkeleton[start.second-1][middleX()] != WALL &&
            level.tileSkeleton[start.second+1][middleX()] == WALL &&
            // No player near
            !(level.startPoint.first in start.first..end.first
                && level.startPoint.second == start.second) &&
            // Big enough
            end.first-start.first > 2
        )
    }
```
[Source](https://github.com/WernerDinges/Birne/blob/master/src/main/kotlin/core/generationEngine/rumble/drillHoles.kt)

Then we look for the longest path across the entire map - this path we'll fill with ladders and platforms and coins.
But we'll omit the pathfinding algorithm and look directly at how the game generates spikes:
```kotlin
fun EngineScope.spikes() {
    for(limb in limbs.list.filter { !it.isVertical && it.canContainDangers }) {

        val success = Random.nextInt(100) < 10 + 20 * (1 - exp(-.5f * level.difficulty))
        if(limb.canContainDangers && success) {
            for(x in (limb.start.first + 1) ..< limb.end.first) {

                // Here we check if all the conditions are met:
                // Not too much other spikes beside, there's floor beneath, etc.

                val y = limb.start.second

                val left = level.tileSkeleton[y][x-1] != SPIKE
                val preleft = level.tileSkeleton[y][x-2] != SPIKE
                val notBlockLeft = !(level.tileSkeleton[y][x-1] != WALL && level.tileSkeleton[y-1][x-1] == WALL)
                val notBlockRight = !(level.tileSkeleton[y][x+1] != WALL && level.tileSkeleton[y-1][x+1] == WALL)
                val hole = level.tileSkeleton[limb.start.second + 1][x - 2] != AIR
                val chance = Random.nextInt(14) < level.difficulty + 7

                if((left || preleft) && hole && chance && notBlockLeft && notBlockRight)
                    level.tileSkeleton[y][x] = SPIKE

            }
        }

    }
}
```
[Source](https://github.com/WernerDinges/Birne/blob/master/src/main/kotlin/core/generationEngine/rumble/spikes.kt)

More interesting details can be found in the [procedural generation engine file](https://github.com/WernerDinges/Birne/blob/master/src/main/kotlin/core/generationEngine/rumble/RumbleEngine.kt).

## Kotlin DSL for Modular Design

One of the core features of **Birne** is its use of Kotlin's ability to build absolutely beuatiful DSLs to streamline and modularize your project. The DSL system in this game allows:
- **Modularity and Flexibility**: Easy adaptation of new procedural generation engines and expansion into new types of rooms, decorations, and challenges.
- **High-Level Development**: By abstracting certain details, the DSL empowers developers to focus on higher-level design elements, simplifying both the creation and maintenance of new content.

I will demonstrate some examples of deep DSL integration into my project. First, let's look at the implementation of the interface responsible for procedural generation:
```kotlin
interface GenerationEngine {
    fun launch(levelConfig: LevelConfig, gameConfig: GameConfig)
}
```
[Source](https://github.com/WernerDinges/Birne/blob/master/src/main/kotlin/core/generationEngine/GenerationEngine.kt)

The implemented object of this interface:
```kotlin
object RumbleEngine: GenerationEngine {
    override fun launch(levelConfig: LevelConfig, gameConfig: GameConfig) = ignite(levelConfig, gameConfig) {

        limbs { generateLimbs() }

        drillHoles()

        longestPath { dfsPath() }

        entities {
            walkers()
            hoppers()
            witches()
        }

        platforms()

        ladders()

        decorations()

        spikes()

        collectables {
            treasures()
        }

        drawStartAndFinish()

    }
}
```
[Source](https://github.com/WernerDinges/Birne/blob/master/src/main/kotlin/core/generationEngine/rumble/RumbleEngine.kt)

As you can see, all stages of map generation are modular. Everything starts with the ```ignite { ... }```, which is described by the following code:
```kotlin
fun ignite(
    level: LevelConfig,
    game: GameConfig,
    scope: EngineScope.() -> Unit
) {
    EngineScope(level, game).apply(scope)
}
```
[Source](https://github.com/WernerDinges/Birne/blob/master/src/main/kotlin/core/generationEngine/common/ignite.kt)

This function includes the initialization of the ```EngineScope``` object. Let's consider it in detail:
```kotlin
class EngineScope(
    var level: LevelConfig,
    var game: GameConfig
) {
    // Some custom data classes for comfort.
    var decoratables = Decoratable()
    var limbs = Limbs()
    var longestPath = LimbPath()

    // Here we simply set all the limbs.
    fun limbs(scope: EngineScope.() -> Limbs) {
        limbs = scope()
    }

    // EngineScope's inner variables manipulations.
    fun longestPath(scope: EngineScope.() -> Triple<LimbPath, Pair<Int, Int>, Pair<Int, Int>>) {
        val (longPath, startAt, endAt) = scope()
        longestPath = longPath
        level.startPoint = startAt
        level.endPoint = endAt
    }

    // Another custom scope to set and manage entities.
    fun entities(scope: EntityScope.() -> Unit) {
        EntityScope(level, game, longestPath).apply(scope)
    }

    // Another custom scope to set and manage coins.
    fun collectables(scope: CollectableScope.() -> Unit) {
        CollectableScope(level, game, longestPath, limbs).apply(scope)
    }

    // The cherry on the cake is that the last thing we do is set up the spawn point and the finish line location.
    fun drawStartAndFinish() {
        level.tileSkeleton[level.startPoint.second][level.startPoint.first] = START
        level.tileSkeleton[level.endPoint.second][level.endPoint.first] = FINISH_OPEN
            //if(level.doorClosed) FINISH_CLOSED else FINISH_OPEN
    }
}
```
[Source](https://github.com/WernerDinges/Birne/blob/master/src/main/kotlin/core/generationEngine/EngineScope.kt)

Another notable thing is the abstraction from framework details. Drawing on the screen is built on drawing inside the Canvas tool,
and the project provides an incomplete but good abstraction to sharpen the focus on higher-level coding tasks:
```kotlin
interface Entity {
    // ...

    val hitbox: Size
    val hitboxOffset: Offset

    var x: Float
    var y: Float

    // Here we delegate all the details of drawing an object on the screen to the object itself,
    // so that we can just call the necessary function later on.
    fun DrawScope.draw(screenWidth: Float, offsetX: Float, offsetY: Float)

    // ...
}
```
[Source](https://github.com/WernerDinges/Birne/blob/master/src/main/kotlin/core/entity/Entity.kt)

```kotlin
data class Player(override var x: Float, override var y: Float, ...): Entity {
    // ...
    override val hitbox = Size(0.5f, 0.75f)
    override val hitboxOffset = Offset(0.25f, 0.25f)

    var vx = 0f
    var vy = 0f

    // ...

    override fun DrawScope.draw(screenWidth: Float, offsetX: Float, offsetY: Float) {

        // Texture selection based on time in ticks and state.
        val texture = when(state) {
            PlayerState.IDLE -> skin.idle
            PlayerState.MOVE -> if(ticks % 250L < 125) skin.move1 else skin.move2
            PlayerState.JUMP -> skin.jump

            else -> skin.idle
        }

        // All the dirty work.
        with(texture) {
            scale(
                scaleX = if(isMirrored) -1f else 1f,
                scaleY = 1f
            ) {
                translate(
                    left = if(!isMirrored) offsetX + x * cellSize + 1f
                        else screenWidth - (offsetX + x * cellSize + 1f) - cellSize,
                    top = offsetY + y * cellSize + 1.5f
                ) {

                    draw(Size(cellSize - 1f, cellSize - 1f))

                }
            }
        }
    }

    // ...
}
```
[Source](https://github.com/WernerDinges/Birne/blob/master/src/main/kotlin/core/entity/player/Player.kt)

Ultimately, using this function looks like this:
```kotlin
    Canvas(/* ... */) {
        with(state.instance.level!!) { translate(left = trigger) {
            drawMap()
            drawUI()
        } }
    }
```
[Source](https://github.com/WernerDinges/Birne/blob/master/src/main/kotlin/screens/LevelScreen.kt)

And the ```drawMap()``` method is part of the Level class:
```kotlin
class Level(
    val config: LevelConfig,
    gameConfig: GameConfig,
    playerInput: PlayerInput
) {
    // ...

    fun DrawScope.drawMap() {
        // ...

        with(player) {
            draw(w, offsetX, offsetY)
        }
    }

    // ...
}
```
[Source](https://github.com/WernerDinges/Birne/blob/master/src/main/kotlin/core/level/Level.kt)

As you can see, everything is implemented incredibly handy for future expansion of the game's features.
Nevertheless, the code is not perfect, and there are always new horizons to strive for.

## Future Plans

Planned features for **Birne** include:
- Enhancing the DSL to further abstract elements of the Compose framework.
- Introducing new enemies, levels, and obstacles for players to encounter.
- Expanding customization options to deepen gameplay variety.
