package dev.cyalc.tictactoe.data

data class Cell(val value: CellType?, val position: Point)

enum class CellType(val displayValue: String) {
    X("X"),
    O("O"),
}

data class Point(val x: Int, val y: Int)

data class Player(
    val chosenSign: CellType,
    val turn: Turn
)

data class Win(
    val player: Player,
    val cells: List<Cell>
)

enum class Turn {
    PLAYER_ONE,
    PLAYER_TWO
}
