package com.oleg1202000.finapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Tab
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oleg1202000.finapp.ui.graphdraw.BarGraph
import com.oleg1202000.finapp.ui.graphdraw.ButtonGraph
import com.oleg1202000.finapp.ui.graphdraw.TableAmount


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    finappStatusbarTitle: MutableState<String>
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var stateTab by rememberSaveable { mutableStateOf(0) }
    val titles = listOf("Расходы", "Доходы")


    if (stateTab == 0) {
        viewModel.setIsIncomeValue(changeValue = false)
        viewModel.updateDataGraph()
    } else {
        viewModel.setIsIncomeValue(changeValue = true)
        viewModel.updateDataGraph()
    }


    LazyColumn(
    ) {

        item {
            TabRow(
                selectedTabIndex = stateTab
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = stateTab == index,
                        onClick = { stateTab = index },
                        text = {
                            Text(
                                text = title,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
            }
        }

        // график 1
        item {
            if (uiState.isLoading || uiState.dataGraph.isEmpty()) {
                finappStatusbarTitle.value = "Главная"

                Card {
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
                }
            } else {

                BarGraph(
                    dataGraph = uiState.dataGraph
                )
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        item {
            ButtonGraph(
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
                dataGraph = uiState.dataGraph
            )
        }

        item {
            Spacer(
                modifier = Modifier.height(80.dp) // Чтобы кнопка "+" не закрывала контент
            )
        }
    }
}
