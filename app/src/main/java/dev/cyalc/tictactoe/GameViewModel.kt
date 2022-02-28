package dev.cyalc.tictactoe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.cyalc.tictactoe.data.*

private val initialBoard = arrayOf(
    arrayOf(
        Cell(null, Point(0, 0)),
        Cell(null, Point(0, 1)),
        Cell(null, Point(0, 2)),
    ),
    arrayOf(
        Cell(null, Point(1, 0)),
        Cell(null, Point(1, 1)),
        Cell(null, Point(1, 2)),
    ),
    arrayOf(
        Cell(null, Point(2, 0)),

        Cell(null, Point(2, 1)),
        Cell(null, Point(2, 2)),
    )
)

class GameViewModel : ViewModel() {
    private val initialGameState = GameState(
        player = Player(
            chosenSign = CellType.X,
            turn = Turn.PLAYER_ONE
        ),
        boardData = initialBoard,
        win = null
    )

    var gameState by mutableStateOf(initialGameState)
        private set

    fun onCellClicked(cell: Cell) {
        val tileInfo = gameState.boardData[cell.position.x][cell.position.y]
        if (tileInfo.value == null && gameState.win == null) {
            nextTurn(cell)
        }
    }

    fun onResetClicked() {
        gameState = initialGameState
    }

    private fun nextTurn(cellClicked: Cell) {
        val currentPlayer = gameState.player
        val currentBoard = gameState.boardData
        val nextBoard = currentBoard.nextBoard(currentPlayer, cellClicked)
        gameState = gameState.copy(
            boardData = nextBoard,
            player = currentPlayer.nextPlayer(),
            win = isWin(nextBoard, currentPlayer)
        )
    }

    private fun isWin(board: Array<Array<Cell>>, currentPlayer: Player): Win? {
        val boardHeight = board.size
        val boardWidth = board[0].size

        fun List<Cell>.constructWin(): Win? {
            if (all { it.value == currentPlayer.chosenSign }) {
                return Win(
                    player = currentPlayer,
                    cells = this
                )
            }

            return null
        }

        fun isRowWinning(): Win? {
            board.forEach { cellRow ->
                cellRow.toList().constructWin()
            }
            return null
        }

        fun isColumnWinning(): Win? {
            for (y in 0 until boardHeight) {
                val columnSet = mutableSetOf<Cell>()
                for (x in 0 until boardWidth) {
                    columnSet.add(board[x][y])
                }

                if (columnSet.toList().constructWin() != null) {
                    return columnSet.toList().constructWin()
                }
            }

            return null
        }

        fun isDiagonalWinning(): Win? {
            val firstDiagonal = board.flatMap {
                setOfNotNull(
                    it.find { it.position.x == it.position.y }
                )
            }
            if (firstDiagonal.all { it.value == currentPlayer.chosenSign }) {
                return Win(
                    player = currentPlayer,
                    cells = firstDiagonal
                )
            }

            val secondDiagonal = board.flatMap {
                setOfNotNull(
                    it.find { it.position.x + it.position.y == 2 }
                )
            }
            if (secondDiagonal.all { it.value == currentPlayer.chosenSign }) {
                return Win(
                    player = currentPlayer,
                    cells = firstDiagonal
                )
            }

            return null
        }


        return isColumnWinning() ?: isRowWinning() ?: isDiagonalWinning()
    }
}

private fun Array<Array<Cell>>.nextBoard(
    currentPlayer: Player,
    cellClicked: Cell
): Array<Array<Cell>> {
    val boardData = this.copy()
    val posX = cellClicked.position.x
    val posY = cellClicked.position.y
    boardData[posX][posY] = cellClicked.copy(value = currentPlayer.chosenSign)
    return boardData
}

private fun Player.nextPlayer() = Player(
    chosenSign = when (chosenSign) {
        CellType.X -> CellType.O
        CellType.O -> CellType.X
    },
    turn = when (turn) {
        Turn.PLAYER_ONE -> Turn.PLAYER_TWO
        Turn.PLAYER_TWO -> Turn.PLAYER_ONE
    }
)

data class GameState(
    val boardData: Array<Array<Cell>>,
    val player: Player,
    val win: Win?
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

fun Array<Array<Cell>>.copy() = Array(size) { get(it).clone() }