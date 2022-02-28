package dev.cyalc.tictactoe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.cyalc.tictactoe.data.Cell
import dev.cyalc.tictactoe.data.Win
import dev.cyalc.tictactoe.ui.theme.BorderColor
import dev.cyalc.tictactoe.ui.theme.CellColor
import dev.cyalc.tictactoe.ui.theme.TileColor

@Composable
fun GameBoard(
    boardData: Array<Array<Cell>>,
    win: Win?,
    onCellClick: (Cell) -> Unit
) {
    Card(
        border = BorderStroke(
            8.dp,
            if (win == null) BorderColor else CellColor
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            boardData.map { cellsRow ->
                Row {
                    cellsRow.map {
                        Square(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            data = it,
                            win = win,
                            onClick = onCellClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Square(
    modifier: Modifier,
    data: Cell,
    win: Win?,
    onClick: (Cell) -> Unit
) {
    val isWinningCell = win?.cells?.contains(data) ?: false

    Surface(
        modifier = modifier.border(
            4.dp,
            if (win != null) CellColor else BorderColor
        ),
        color = TileColor
    ) {
        Box(
            modifier.clickable {
                onClick.invoke(data)
            },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = data.value?.displayValue ?: "",
                color = CellColor,
                fontWeight = FontWeight.Bold,
                fontSize = if (isWinningCell) 60.sp else 48.sp,
                fontFamily = FontFamily.SansSerif,
                textDecoration = if (isWinningCell) TextDecoration.LineThrough else null
            )
        }
    }
}

