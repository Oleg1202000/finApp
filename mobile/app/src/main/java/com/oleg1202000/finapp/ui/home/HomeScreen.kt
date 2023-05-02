package com.oleg1202000.finapp.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.oleg1202000.finapp.ui.components.FinappFloatingActionButton
import com.oleg1202000.finapp.ui.components.FinappNavigationBar
import com.oleg1202000.finapp.ui.components.FinappStatusBar
import com.oleg1202000.finapp.ui.theme.Shapes
import com.oleg1202000.finapp.ui.theme.Typography
import com.oleg1202000.finapp.ui.theme.defaultColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    navController: NavHostController,
    currentDestination: NavDestination?
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val defaultColor = defaultColor


    Scaffold(
        topBar = {
            FinappStatusBar(
                item = "- ${uiState.sumAmount}    + 0",  // Для отображения суммы доходов и расходов
            )
        },

        bottomBar = {
            FinappNavigationBar(
                navController = navController,
                currentDestination = currentDestination,
            )
        },

        floatingActionButton = {
            FinappFloatingActionButton(
                navController = navController,
                currentDestination = currentDestination
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {

            item {
                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            }

            // график1
            item {
                if (uiState.sumAmount == 0) {
                    Column(
                        modifier = Modifier
                            .height(300.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = "Нет записей о расходах")
                        }
                    }
                } else {
                    GraphAmount(
                        dataHome = uiState.dataHome
                    )
                }
            }


            item { Spacer(modifier = Modifier.height(20.dp)) }


            // "таблица" из категорий и трат
            item {
                TableAmount(
                    dataHome = uiState.dataHome,
                    sumAmount = uiState.sumAmount
                )
            }
        }
    }
}


@Composable
fun GraphAmount(
    dataHome: List<DataHome>,
) {
    //val swipeableState = rememberSwipeableState(initialValue = )


    val showDetailedInfo = remember { mutableStateOf(false) }


    Column {
        Surface(
            modifier = Modifier
                .fillMaxSize()
            // TODO:   .swipeable()
            ,
            shape = Shapes.small,
            shadowElevation = 4.dp
        ) {

            Column {

                dataHome.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showDetailedInfo.value = !showDetailedInfo.value
                            },

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
                            modifier = Modifier.fillMaxSize()
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
                                text = (item.amount.toString() + " ₽" ),
                                style = Typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

            // Кнопки День / неделя / месяц
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(onClick = {
                    /*TODO*/
                }
                ) {
                    Text(text = GraphPeriod.Day.toString())
                }

                Button(onClick = {
                   // deltaPeriod = 0
                }) {
                    Text(text = GraphPeriod.Week.toString())
                }

                Button(onClick = { /*TODO*/ }) {
                    Text(text = GraphPeriod.Month.toString())
                }
            }

        Spacer(modifier = Modifier.height(20.dp))
    }
}


@Composable
fun TableAmount(
    dataHome: List<DataHome>,
    sumAmount: Int
) {
    Surface (
        modifier = Modifier.fillMaxSize(),
        shape = Shapes.small,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            dataHome.forEach {item ->
                Row(
                    modifier = Modifier.fillMaxSize(),
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

                    Column {
                        Text(
                            text = item.amount.toString() + " /",
                            fontStyle = FontStyle.Italic
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
