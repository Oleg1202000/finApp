package com.oleg1202000.finapp.ui.plan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oleg1202000.finapp.ui.graphdraw.BarGraph
import com.oleg1202000.finapp.ui.graphdraw.ButtonGraph
import com.oleg1202000.finapp.ui.graphdraw.TableAmount


@Composable
fun PlanScreen(
    viewModel: PlanViewModel = viewModel(),
    finappStatusBarTitle: MutableState<String>
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    LazyColumn {

        // график 1
        item {
            if (uiState.isLoading || uiState.dataGraph.isEmpty()) {
                finappStatusBarTitle.value = "Потрачено / запланировано:    0 / 0 ₽" // Для отображения суммы доходов и расходов
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
                            Text(text = "Нет записей о расходах")
                        }
                    }
                }
            } else {

                finappStatusBarTitle.value = "Потрачено / запланировано:    ${uiState.sumFact} / ${uiState.sumPlanned} ₽"
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