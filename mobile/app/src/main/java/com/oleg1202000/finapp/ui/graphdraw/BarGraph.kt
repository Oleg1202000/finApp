package com.oleg1202000.finapp.ui.graphdraw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.oleg1202000.finapp.ui.theme.Shapes
import com.oleg1202000.finapp.ui.theme.Typography
import com.oleg1202000.finapp.ui.theme.notOk80Color
import com.oleg1202000.finapp.ui.theme.notOkColor
import com.oleg1202000.finapp.ui.theme.okColor


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BarGraph(
    dataGraph: List<DataGraph>
) {
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val widthDp = LocalConfiguration.current.screenWidthDp
    val anchors = mapOf(0f to 0, (widthDp / 2).toFloat() to 1, widthDp.toFloat() to 2)

    val showDetailedInfo = remember { mutableStateOf(false) }


    Surface(
        modifier = Modifier
            .defaultMinSize(
                minHeight = 300.dp
            )
            .fillMaxSize()
            .swipeable(
                state = swipeableState,
                orientation = Orientation.Horizontal,
                anchors = anchors
            ),
        shape = Shapes.small,
        shadowElevation = 4.dp
    ) {

        Column {
            dataGraph.forEach { item ->
                val colorItem = if (item.colorItem == ColorGraph.NOT_OK_COLOR) {
                    notOkColor
                } else if (item.colorItem == ColorGraph.NOT_OK_80_COLOR) {
                    notOk80Color
                } else if (item.colorItem == ColorGraph.OK_COLOR) {
                    okColor
                } else {
                    MaterialTheme.colorScheme.tertiaryContainer
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth(),

                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        painter = painterResource(id = item.iconCategory),
                        contentDescription = item.categoryName,
                        tint = Color(item.colorIcon.toULong())
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Canvas(
                            modifier = Modifier
                                .fillMaxSize()
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onPress = {
                                            showDetailedInfo.value = !showDetailedInfo.value
                                            awaitRelease()
                                            showDetailedInfo.value = !showDetailedInfo.value
                                        }

                                    )
                                }


                        ) {

                            val sizeHeight = 30f
                            drawRect(
                                color = colorItem,
                                /* Добавляем отступ, чтобы прямоугольник
                                находился по центру Row() */
                                topLeft = Offset(
                                    x = 0f,
                                    y = size.height * 0.20f
                                ),
                                size = Size(
                                    size.width * item.coefficientAmount,
                                    sizeHeight.dp.toPx()
                                )
                            )
                        }


                        if (showDetailedInfo.value) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(text = item.categoryName)
                            }
                        }
                    }


                    if (!showDetailedInfo.value) {
                        Text(
                            text = ((item.coefficientAmount * 100).toInt().toString() + " %"),
                            style = Typography.bodyMedium
                        )
                    } else {
                        Text(
                            text = (item.amount.toString() + " ₽"),
                            style = Typography.bodyMedium
                        )
                    }
                }
            }
        }

    }
}