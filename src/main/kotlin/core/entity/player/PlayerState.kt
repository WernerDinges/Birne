package core.entity.player

enum class PlayerState {

    // Staying in a fixed position
    IDLE,
    // Moving horizontally
    MOVE,
    // Jumping upwards and falling after it
    JUMP,
    // Climbing up/down
    CLIMB,
    // Holding S not to stand on the platform
    DROP

}