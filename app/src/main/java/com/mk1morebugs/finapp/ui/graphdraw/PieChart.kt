package com.mk1morebugs.finapp.ui.graphdraw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oleg1202000.finapp.R


@Composable
fun PieChart(
    dataPie: List<DetailData>,
    sumIncome: Int,
) {

    BoxWithConstraints(
        modifier = Modifier
    ) {

        var startAngle = -90f
        val coordinateCentreCircleX = minWidth * 0.5f
        val coordinateCentreCircleY = minHeight * 0.5f

        Canvas(
            modifier = Modifier
                .width(coordinateCentreCircleX * 2)
                .pointerInput(Unit) {

                    detectTapGestures(
                        onPress = {
                            // TODO:
                            awaitRelease()
                        }
                    )
                }
        ) {
            dataPie.forEachIndexed { index, item ->
                val widthArc = if (false) Stroke(60f) else Stroke(30f, cap = StrokeCap.Round) // TODO:
                val sweepAngle = item.factAmount / sumIncome.toFloat() * 360f
                drawArc(
                    color = Color(item.colorIcon.toULong()),
                    startAngle = startAngle,
                    sweepAngle = sweepAngle - 10,
                    useCenter = false,
                    style = widthArc,
                    size = Size(
                        coordinateCentreCircleX.toPx() * 1.2f, // 1.2 + 2 * 0.4 == 2.0
                        coordinateCentreCircleX.toPx() * 1.2f,
                    ),
                    topLeft = Offset(x = coordinateCentreCircleX.toPx() * 0.4f, y = 50f),
                )
                startAngle += sweepAngle
            }
        }

        Text(
            modifier = Modifier
                .offset(
                    coordinateCentreCircleX.minus(40.dp),
                    coordinateCentreCircleY.minus(20.dp),
                ),
            text = "${sumIncome.toString()} ₽",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}


@Preview
@Composable
fun PieChartPreview() {
    PieChart(
        dataPie = previewData,
        sumIncome = 0,
    )
}

val previewData = listOf(
    DetailData(
        categoryName = "previewCategory 1",
        iconCategory = R.drawable.ic_category_apartment_40px,
        colorIcon = Color(0xFF00BFA5).value.toLong(),
        factAmount = 100,
        planAmount = 500,
    ),
    DetailData(
        categoryName = "previewCategory 2",
        iconCategory = R.drawable.ic_category_apartment_40px,
        colorIcon = Color(0xFF00BFA5).value.toLong(),
        factAmount = 200,
        planAmount = 500,
    ),
    DetailData(
        categoryName = "previewCategory 3",
        iconCategory = R.drawable.ic_category_apartment_40px,
        colorIcon = Color(0xFF00BFA5).value.toLong(),
        factAmount = 200,
        planAmount = 500,
    ),
)