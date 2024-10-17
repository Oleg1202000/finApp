package com.mk1morebugs.finapp.ui.graphdraw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mk1morebugs.finapp.R
import com.mk1morebugs.finapp.ui.theme.FinappTheme
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    pieChartItems: List<CategoryDetails>,
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val coordinateCenter = maxWidth * 0.5f
        val arcStrokeTotalWidth = 30f

        val sumAmount = pieChartItems.sumOf { it.categoryAmount }
        var offsetAngle = -90f
        var pieChartData: List<PieChartData> by remember {
            mutableStateOf(
                pieChartItems.map { item ->
                    val sweepAngle = item.categoryAmount / sumAmount.toFloat() * 360f
                    val data = PieChartData(
                        startAngle = offsetAngle,
                        sweepAngle = sweepAngle,
                        arcStrokeWidth = arcStrokeTotalWidth,
                        pieChartItemColor = item.categoryIconColor,
                        pieChartItemAmount = item.categoryAmount
                    )
                    offsetAngle += sweepAngle
                    data
                }
            )
        }
        var pieChartSumText by remember { mutableIntStateOf(sumAmount) }

        Canvas(
            modifier = Modifier
                .size(width = minWidth, height = minHeight)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()

                            // Shifting the initial coordinates to the center of the draw scope
                            val gesturePositionX =
                                event.changes.first().position.x - coordinateCenter.toPx()
                            val gesturePositionY =
                                coordinateCenter.toPx() - event.changes.first().position.y

                            // Let's move on to polar coordinates
                            val gestureRadius =
                                sqrt(gesturePositionX.pow(2) + gesturePositionY.pow(2))
                            val gestureDegree: Float = (
                                    atan2(
                                        y = gesturePositionY,
                                        x = gesturePositionX
                                    ) * 180f / PI
                                    ).toFloat()

                            /* Rotate the angle 90 degrees counterclockwise
                            according to startAngle in drawArc().
                            So the angle takes values from -90 degrees to 270 degrees */
                            val gestureDegreeInPieChart = if (gestureDegree < 90) {
                                gestureDegree.unaryMinus()
                            } else {
                                360f - gestureDegree
                            }

                            pieChartData =
                                if (gestureRadius > coordinateCenter.toPx() - 42f &&
                                    gestureRadius < coordinateCenter.toPx() + 42f
                                ) {
                                    pieChartData.map {
                                        if (gestureDegreeInPieChart > it.startAngle &&
                                            gestureDegreeInPieChart < it.startAngle + it.sweepAngle
                                        ) {
                                            pieChartSumText = it.pieChartItemAmount
                                            it.copy(
                                                arcStrokeWidth = 3 * arcStrokeTotalWidth,
                                            )
                                        } else {
                                            it.copy(
                                                arcStrokeWidth = arcStrokeTotalWidth,
                                            )
                                        }
                                    }
                                } else {
                                    pieChartData.map {
                                        pieChartSumText = sumAmount
                                        it.copy(
                                            arcStrokeWidth = arcStrokeTotalWidth,
                                        )
                                    }
                                }
                        }
                    }
                }
        ) {
            for (item in pieChartData) {
                // TODO: Учесть значения <5-10 градусов
                val arcStroke = Stroke(item.arcStrokeWidth, cap = StrokeCap.Round)
                val deltaAngle = if (pieChartItems.size == 1) 0 else 10

                drawArc(
                    color = Color(item.pieChartItemColor),
                    startAngle = item.startAngle + deltaAngle,
                    sweepAngle = item.sweepAngle - deltaAngle,
                    useCenter = false,
                    style = arcStroke,
                    size = Size(
                        width = maxWidth.toPx() - item.arcStrokeWidth,
                        height = maxHeight.toPx() - item.arcStrokeWidth,
                    ),
                    topLeft = Offset(
                        x = item.arcStrokeWidth / 2,
                        y = item.arcStrokeWidth / 2
                    ),
                )
            }
        }

        // (radius - arc width / 2) * sine 45 degrees
        // maxWidth = diameter = 2 * radius
        val textSize = (maxWidth.value - arcStrokeTotalWidth) * sin(45f * PI / 180)
        val textOffset = coordinateCenter.value - textSize / 2
        Box(
            modifier = Modifier
                .size(
                    width = textSize.dp,
                    height = textSize.dp
                )
                .offset(
                    x = textOffset.dp,
                    y = textOffset.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$pieChartSumText ₽",
                style = MaterialTheme.typography.displayMedium,
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
    FinappTheme {
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

        PieChart(
            modifier = Modifier.size(150.dp),
            pieChartItems = dataPie,
        )
    }
}