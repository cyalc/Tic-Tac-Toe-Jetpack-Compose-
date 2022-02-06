package dev.cyalc.tictactoe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.cyalc.tictactoe.data.*

private val initialBoard = arrayOf(
    arrayOf(
        Pawn(null, Point(0, 0)),
        Pawn(null, Point(0, 1)),
        Pawn(null, Point(0, 2)),
    ),
    arrayOf(
        Pawn(null, Point(1, 0)),
        Pawn(null, Point(1, 1)),
        Pawn(null, Point(1, 2)),
    ),
    arrayOf(
        Pawn(null, Point(2, 0)),

        Pawn(null, Point(2, 1)),
        Pawn(null, Point(2, 2)),
    )
)

class GameViewModel : ViewModel() {
    private val initialGameState = GameState(
        player = Player(
            chosenSign = PawnType.X,
            turn = Turn.PLAYER_ONE
        ),
        boardData = initialBoard
    )

    var gameState by mutableStateOf(initialGameState)
        private set

    fun onPawnClicked(pawn: Pawn) {
        val tileInfo = gameState.boardData[pawn.position.x][pawn.position.y]
        if (tileInfo.value == null) nextTurn(pawn)
    }

    fun onResetClicked() {
        gameState = initialGameState
    }

    private fun nextTurn(pawnClicked: Pawn) {
        val currentPlayer = gameState.player
        val currentBoard = gameState.boardData
        gameState = gameState.copy(
            boardData = currentBoard.nextBoard(currentPlayer, pawnClicked),
            player = currentPlayer.nextPlayer()
        )
    }
}

private fun Array<Array<Pawn>>.nextBoard(
    currentPlayer: Player,
    pawnClicked: Pawn
): Array<Array<Pawn>> {
    val boardData = this.copy()
    val posX = pawnClicked.position.x
    val posY = pawnClicked.position.y
    boardData[posX][posY] = pawnClicked.copy(value = currentPlayer.chosenSign)
    return boardData
}

private fun Player.nextPlayer() = Player(
    chosenSign = when (chosenSign) {
        PawnType.X -> PawnType.O
        PawnType.O -> PawnType.X
    },
    turn = when (turn) {
        Turn.PLAYER_ONE -> Turn.PLAYER_TWO
        Turn.PLAYER_TWO -> Turn.PLAYER_ONE
    }
)

data class GameState(
    val boardData: Array<Array<Pawn>>,
    val player: Player
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameState

        if (!boardData.contentDeepEquals(other.boardData)) return false

        return true
    }

    override fun hashCode(): Int {
        return boardData.contentDeepHashCode()
    }
}

fun Array<Array<Pawn>>.copy() = Array(size) { get(it).clone() }