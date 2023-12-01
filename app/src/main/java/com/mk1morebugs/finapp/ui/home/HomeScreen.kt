package com.mk1morebugs.finapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Tab
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mk1morebugs.finapp.ui.graphdraw.PieChart
import com.mk1morebugs.finapp.ui.graphdraw.ButtonGraph
import com.mk1morebugs.finapp.ui.graphdraw.TableAmount
import com.mk1morebugs.finapp.ui.theme.Shapes


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    finappStatusbarTitle: MutableState<String>,
) {
    finappStatusbarTitle.value = "Главная"

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var stateTab by rememberSaveable { mutableIntStateOf(0) }
    val titles = listOf("Факт", "План")

    /*val swipeableState = rememberSwipeableState(initialValue = 0)
    val widthDp = LocalConfiguration.current.screenWidthDp
    val anchors = mapOf(0f to 0, (widthDp / 2).toFloat() to 1, widthDp.toFloat() to 2)
    */


    Column(
        modifier = Modifier.
        fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = stateTab
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = stateTab == index,
                    onClick = {
                        stateTab = index
                        if (stateTab == 1) {
                            viewModel.switchIsFactIncomeValue(false)
                        } else {
                            viewModel.switchIsFactIncomeValue(true)
                        }
                        viewModel.updateDataGraph()
                              },
                    text = {
                    Text(
                        text = title, maxLines = 2, overflow = TextOverflow.Ellipsis
                    )
                    }
                )
            }
        }

        // график 1
        Surface(
            modifier = Modifier
                .defaultMinSize(
                    minHeight = 300.dp,
                )
                .fillMaxWidth()
                /*.swipeable(
                    state = swipeableState,
                    orientation = Orientation.Horizontal,
                    anchors = anchors
                )*/,
            shape = Shapes.small,
            shadowElevation = 4.dp
        )  {
           if (uiState.isLoading || uiState.detailData.isEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()

                        .size(300.dp)
                        .background(MaterialTheme.colorScheme.background),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Text(text = "Нет записей за выбранный период")
                    }
                }
            } else {
                PieChart(
                    dataPie = uiState.detailData,
                    sumIncome = uiState.sumIncome,
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        ButtonGraph(beginDate = uiState.beginDate,
            endDate = uiState.endDate,
            updateDate = { viewModel.getDate(it) },
            updateDataGraph = { viewModel.updateDataGraph() },
            updateGraphPeriod = { viewModel.updateGraphPeriod(it) })

        Spacer(modifier = Modifier.height(20.dp))

        // таблица из категорий и трат
        TableAmount(
            detailData = uiState.detailData,
            sumIncome = uiState.sumIncome
        )

    }
}
