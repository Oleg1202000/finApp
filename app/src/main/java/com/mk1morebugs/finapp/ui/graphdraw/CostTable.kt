package com.mk1morebugs.finapp.ui.graphdraw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun CostTable(
    modifier: Modifier = Modifier,
    detailData: List<CategoryDetails>,
    sumIncome: Int,
) {
    Surface(
        shape = Shapes.small,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (costItem in detailData) {
                val backgroundColor =
                    if (costItem.categorySummaryCost / sumIncome.toFloat() > 1) {
                        redColor
                    } else if (costItem.categorySummaryCost / sumIncome.toFloat() >= 0.8) {
                        yellowColor
                    } else {
                        MaterialTheme.colorScheme.background
                    }

                CostTableItemCard(
                    backgroundColor = backgroundColor,
                    categoryName = costItem.categoryName,
                    categoryIconId = costItem.categoryIconId,
                    categoryIconColor = costItem.categoryIconColor,
                    categorySummaryCost = costItem.categorySummaryCost,
                )
            }
        }
    }
}

@Composable
private fun CostTableItemCard(
    backgroundColor: Color,
    categoryName: String,
    categoryIconId: Int,
    categoryIconColor: ULong,
    categorySummaryCost: Int,
) {
    Row(
        modifier = Modifier
            .padding(
                bottom = 8.dp,
                top = 8.dp,
            )
            .fillMaxWidth()
            .background(color = backgroundColor),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Icon(
            painter = painterResource(id = categoryIconId),
            contentDescription = categoryName,
            tint = Color(categoryIconColor)
        )
        Spacer(Modifier.size(10.dp))

        Text(
            text = categoryName
        )
        Spacer(Modifier.size(10.dp))

        Text(
            text = "$categorySummaryCost ₽",
            fontStyle = FontStyle.Italic,
        )
    }
}