package dev.cyalc.tictactoe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.cyalc.tictactoe.ui.theme.BorderColor
import dev.cyalc.tictactoe.ui.theme.PawnColor
import dev.cyalc.tictactoe.ui.theme.TileColor

@Preview
@Composable
fun GameBoard() {
    Card(
        border = BorderStroke(
            8.dp,
            BorderColor
        ), shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            GameRow()
            GameRow()
            GameRow()
        }
    }
}

@Composable
fun GameRow() {
    Row {
        for (i in 1..3) {
            Square(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            )
        }
    }
}

@Composable
fun Square(modifier: Modifier) {
    Surface(
        modifier = modifier.border(4.dp, BorderColor),
        color = TileColor
    ) {
        Box(modifier, contentAlignment = Alignment.Center) {
            Text(
                text = "X",
                color = PawnColor,
                fontWeight = FontWeight.Bold,
                fontSize = 48.sp,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}