package com.mk1morebugs.finapp.ui.plan

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
import com.mk1morebugs.finapp.ui.graphdraw.PieChart
import com.mk1morebugs.finapp.ui.graphdraw.ButtonGraph
import com.mk1morebugs.finapp.ui.graphdraw.TableAmount


@Composable
fun PlanScreen(
    viewModel: PlanViewModel = viewModel(),
    finappStatusBarTitle: MutableState<String>
) {

    finappStatusBarTitle.value = "запланировано" // Для отображения суммы доходов и расходов

    Text(text = "Планируется удалить")
}