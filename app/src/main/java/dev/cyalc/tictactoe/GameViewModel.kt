package dev.cyalc.tictactoe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.cyalc.tictactoe.data.Pawn
import dev.cyalc.tictactoe.data.PawnType
import dev.cyalc.tictactoe.data.Point

class GameViewModel : ViewModel() {
    private val initialGameState = GameState(
        player = Player(
            chosenSign = PawnType.X,
            turn = Turn.PLAYER_ONE
        ),
        boardData = arrayOf(
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
    )

    var gameState by mutableStateOf(initialGameState)
        private set

    fun onPawnClicked(pawn: Pawn) {
        val tileInfo = gameState.boardData[pawn.position.x][pawn.position.y]
        if (tileInfo.value != null) return

        val currPlayer = gameState.player

        val boardData = gameState.boardData.copy()
        boardData[pawn.position.x][pawn.position.y] = pawn.copy(value = currPlayer.chosenSign)

        gameState = gameState.copy(
            boardData = boardData,
            player = gameState.player.nextPlayer()
        )
    }
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

fun Array<Array<Pawn>>.copy() = Array(size) { get(it).clone() }

data class GameState(val boardData: Array<Array<Pawn>>, val player: Player) {


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

data class Player(
    val chosenSign: PawnType,
    val turn: Turn
)

enum class Turn {
    PLAYER_ONE,
    PLAYER_TWO
}
