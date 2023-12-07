package com.mk1morebugs.finapp.ui.graphdraw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.unit.dp


@Composable
fun PieChart(
    dataPie: List<DetailData>,
    sumIncome: Int,
    ) {
    BoxWithConstraints(
        modifier = Modifier
    ) {

        var startAngle = -90f
        var delta = 10
        if (dataPie.size == 1){
            delta = 0
        }
        val coordinateCentreCircleX = minWidth * 0.5f
        val coordinateCentreCircleY = minHeight * 0.5f

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(minHeight)
                .pointerInput(Unit) {

                    detectTapGestures(
                        onPress = {
                            // TODO: write tap handler
                            awaitRelease()

                        }
                    )
                }

        ) {
            dataPie.forEachIndexed { index, item ->
                val widthArc = Stroke(30f, cap = StrokeCap.Round)
                val sweepAngle = item.factAmount / sumIncome.toFloat() * 360f // TODO: Учесть значения <5-10 градусов 

                drawArc(
                    color = Color(item.colorIcon.toULong()),
                    startAngle = startAngle,
                    sweepAngle = sweepAngle - delta,
                    useCenter = false,
                    style = widthArc,
                    size = Size(
                        minHeight.toPx() * 0.8f, // 1.2 + 2 * 0.4 == 2.0
                        minHeight.toPx() * 0.8f,
                    ),
                    topLeft = Offset(
                        x = (coordinateCentreCircleX - coordinateCentreCircleY * 0.8f).toPx(),
                        y = 50f
                    ),
                )
                startAngle += sweepAngle
            }
        }

        Text(
            modifier = Modifier
                .offset(
                    coordinateCentreCircleX.minus(40.dp),
                    coordinateCentreCircleY.minus(10.dp),
                ),
            text = "${sumIncome} ₽",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}
