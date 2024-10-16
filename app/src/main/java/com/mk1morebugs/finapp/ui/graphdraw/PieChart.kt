package com.mk1morebugs.finapp.ui.graphdraw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mk1morebugs.finapp.R
import com.mk1morebugs.finapp.ui.theme.FinappTheme
import kotlin.math.sin

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    pieChartItems: List<CategoryDetails>,
    sumAmount: Int,
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val coordinateCenter = maxWidth * 0.5f
        val arcStrokeWidth = 30f

        Canvas(
            modifier = Modifier
                .size(width = minWidth, height = minHeight)
        ) {
            var offsetAngle = -90f
            val deltaAngle = if (pieChartItems.size == 1) 0 else 10

            pieChartItems.forEach { item ->
                val arcStroke = Stroke(arcStrokeWidth, cap = StrokeCap.Round)
                val sweepAngle = item.categoryAmount / sumAmount.toFloat() * 360f // TODO: Учесть значения <5-10 градусов
                drawArc(
                    color = Color(item.categoryIconColor),
                    startAngle = offsetAngle + deltaAngle,
                    sweepAngle = sweepAngle - deltaAngle,
                    useCenter = false,
                    style = arcStroke,
                    size = Size(
                        width = maxWidth.toPx() - arcStrokeWidth,
                        height = maxHeight.toPx() - arcStrokeWidth,
                    ),
                    topLeft = Offset(
                        x = arcStrokeWidth / 2,
                        y = arcStrokeWidth / 2
                    ),
                )
                offsetAngle += sweepAngle
            }
        }

        // (radius - arc width / 2) * sine 45 degrees
        // maxWidth = diameter = 2 * radius
        val textSize = (maxWidth.value - arcStrokeWidth) * sin(45f * kotlin.math.PI / 180)
        val textOffset = coordinateCenter.value - textSize / 2
        Box(
            modifier = Modifier
                .size(width = textSize.dp,
                    height = textSize.dp
                )
                .offset(
                    x = textOffset.dp,
                    y = textOffset.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$sumAmount ₽",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PieChartPreview() {
    FinappTheme{
        val dataPie: List<CategoryDetails> = listOf(
            CategoryDetails(
                categoryName = "categoryName 1",
                categoryIconId = R.drawable.ic_category_coffee_40px,
                categoryIconColor = Color.Cyan.value,
                categoryAmount = 1000
            ),
            CategoryDetails(
                categoryName = "categoryName 1",
                categoryIconId = R.drawable.ic_category_coffee_40px,
                categoryIconColor = Color.Yellow.value,
                categoryAmount = 500
            ),
            CategoryDetails(
                categoryName = "categoryName 1",
                categoryIconId = R.drawable.ic_category_coffee_40px,
                categoryIconColor = Color.Magenta.value,
                categoryAmount = 500
            )
        )
        val sumAmount = dataPie.sumOf { it.categoryAmount }

        PieChart(
            modifier = Modifier.size(150.dp),
         pieChartItems = dataPie,
            sumAmount = sumAmount
        )
    }
}