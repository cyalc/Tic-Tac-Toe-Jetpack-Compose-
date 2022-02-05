package dev.cyalc.tictactoe.data

data class Pawn(val value: PawnType?, val position: Point)

enum class PawnType(val displayValue: String) {
    X("X"),
    O("O"),
}

data class Point(val x: Int, val y: Int)