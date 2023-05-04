package com.oleg1202000.finapp.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oleg1202000.finapp.ui.theme.Shapes
import com.oleg1202000.finapp.ui.theme.Typography
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LazyColumn(
    ) {


        // график1
        item {
            if (uiState.sumAmount == 0) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 200.dp
                        ),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(text = "Нет записей о расходах")
                }
            } else {
                GraphAmount(
                    dataGraph = uiState.dataGraph
                )
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        item {
            ButtonSelect(
                beginDate = uiState.beginDate,
                endDate = uiState.endDate,
                updateDate = { viewModel.getDate(it) },
                updateDataGraph = { viewModel.updateDataGraph() },
                updateGraphPeriod = { viewModel.updateGraphPeriod(it) }
            )
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        // "таблица" из категорий и трат
        item {
            TableAmount(
                dataGraph = uiState.dataGraph,
                sumAmount = uiState.sumAmount
            )
        }

        item {
            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GraphAmount(
    dataGraph: List<DataGraph>
) {
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val widthDp = LocalConfiguration.current.screenWidthDp
    val anchors = mapOf(0f to 0, (widthDp / 2).toFloat() to 1, widthDp.toFloat() to 2)

    val showDetailedInfo = remember { mutableStateOf(false) }


    Surface(
        modifier = Modifier
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),

                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        painter = painterResource(id = item.iconCategory),
                        contentDescription = item.categoryName,
                        tint = Color(item.colorIconCategory.toULong())
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
                                        }

                                    )
                                }

                        ) {

                            val sizeHeight = 30f
                            drawRect(
                                color = item.colorItem,
                                topLeft = Offset(
                                    x = 0f,
                                    y = size.height * 0.20f
                                ),
                                size = Size(
                                    (size.width) * item.coefficientAmount,
                                    sizeHeight.dp.toPx()
                                )
                            )
                        }


                        if (showDetailedInfo.value) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Start,
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

// FIXME:
/*if (swipeableState.direction == 1f) {
    delta += 1
} else if (swipeableState.direction == -1f) {
    delta -= 1
}
Log.d("delta", delta.toString())
updateDate(delta)*/


@Composable
fun TableAmount(
    dataGraph: List<DataGraph>,
    sumAmount: Int
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
                        tint = Color(item.colorIconCategory.toULong())
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
                            text = sumAmount.toString() + " ₽",
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ButtonSelect(
    beginDate: Long,
    endDate: Long,
    updateDate: (Int) -> Unit,
    updateDataGraph: () -> Unit,
    updateGraphPeriod: (GraphPeriod) -> Unit
) {
    val delta: MutableState<Int> = remember { mutableStateOf(0) }


    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.US)

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            onClick = {
                delta.value -= 1
                updateDate(delta.value)
                updateDataGraph()
            }
        ) {
            Text(
                text = "<"
            )
        }

        Spacer(modifier = Modifier.width(20.dp))
        Text(text = format.format(beginDate))

        Text(text = "-")

        Text(text = format.format(endDate))
        Spacer(modifier = Modifier.width(20.dp))



        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            onClick = {
                delta.value += 1
                updateDate(delta.value)
                updateDataGraph()
            }
        ) {
            Text(
                text = ">"
            )
        }
    }

    Spacer(modifier = Modifier.height(20.dp))


    // Кнопки День / неделя / месяц
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Button(onClick = {
            delta.value = 0
            updateGraphPeriod(GraphPeriod.DAY)
            updateDate(delta.value)
            updateDataGraph()

        }
        ) {
            Text(text = "День")
        }

        Button(onClick = {
            delta.value = 0
            updateGraphPeriod(GraphPeriod.Week)
            updateDate(delta.value)
            updateDataGraph()
        }
        ) {
            Text(text = "Неделя")
        }

        Button(onClick = {
            delta.value = 0
            updateGraphPeriod(GraphPeriod.Month)
            updateDate(delta.value)
            updateDataGraph()
        }) {
            Text(text = "Месяц")
        }
    }

    Spacer(modifier = Modifier.height(20.dp))
}
