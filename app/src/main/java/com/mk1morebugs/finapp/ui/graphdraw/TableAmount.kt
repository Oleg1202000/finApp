package com.mk1morebugs.finapp.ui.graphdraw

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.mk1morebugs.finapp.ui.theme.Shapes


@Composable
fun TableAmount(
    dataGraph: List<DataGraph>
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        shape = Shapes.small,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            dataGraph.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            bottom = 15.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Icon(
                        painter = painterResource(id = item.iconCategory),
                        contentDescription = item.categoryName,
                        tint = Color(item.colorIcon.toULong())
                    )

                    Text(
                        text = item.categoryName
                    )

                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = item.amount.toString() + " /",
                            fontStyle = FontStyle.Italic,

                            )
                        Text(
                            text = item.sumAmount.toString() + " ₽",
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }
        }
    }
}