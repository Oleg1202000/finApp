package com.mk1morebugs.finapp.ui.graphdraw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.mk1morebugs.finapp.ui.theme.redColor
import com.mk1morebugs.finapp.ui.theme.yellowColor


@Composable
fun TableAmount(
    detailData: List<DetailData>,
    sumIncome: Int,
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        shape = Shapes.small,
        shadowElevation = 4.dp
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

                items(detailData) {
                    val backgroundColor =
                        if (it.planAmount != null && it.factAmount / sumIncome.toFloat() > 1) {
                            redColor
                        } else if (it.planAmount != null && it.factAmount / sumIncome.toFloat() >= 0.8) {
                            yellowColor
                        } else {
                            MaterialTheme.colorScheme.background
                        }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            bottom = 15.dp
                        )
                        .background(color = backgroundColor)
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Icon(
                        painter = painterResource(id = it.iconCategory),
                        contentDescription = it.categoryName,
                        tint = Color(it.colorIcon.toULong())
                    )

                    Text(
                        text = it.categoryName
                    )

                    Text(
                        text = it.factAmount.toString() + " ₽",
                        fontStyle = FontStyle.Italic,
                        )
                }
            }
        }
    }
}