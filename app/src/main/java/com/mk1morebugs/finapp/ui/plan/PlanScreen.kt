package com.mk1morebugs.finapp.ui.plan

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun PlanScreen(
    viewModel: PlanViewModel = viewModel(),
    finappStatusBarTitle: MutableState<String>
) {

    finappStatusBarTitle.value = "запланировано" // Для отображения суммы доходов и расходов

    Text(text = "Планируется удалить")
}