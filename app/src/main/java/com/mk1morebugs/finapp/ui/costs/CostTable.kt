package com.mk1morebugs.finapp.ui.costs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun CostTable(
    modifier: Modifier = Modifier,
    detailData: List<CategoryDetails>,
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
                CostTableItemCard(
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
            .fillMaxWidth(),
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