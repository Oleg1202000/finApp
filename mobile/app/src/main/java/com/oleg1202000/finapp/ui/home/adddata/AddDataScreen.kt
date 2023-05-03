@file:OptIn(ExperimentalMaterial3Api::class)

package com.oleg1202000.finapp.ui.home.adddata

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.oleg1202000.finapp.ui.categories.CategoriesScreen
import com.oleg1202000.finapp.ui.theme.Shapes


@Composable
fun  AddDataScreen(
    viewModel: AddDataViewModel = viewModel(),
    navController: NavHostController,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    val snackState = remember { SnackbarHostState() }
    SnackbarHost(hostState = snackState, Modifier)


    val showBottomSheet = rememberSaveable { mutableStateOf(false) }
    var skipPartiallyExpanded by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )

    val openDateDialog = rememberSaveable() { mutableStateOf(false) }


        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(200.dp))

            TextField(
                value = uiState.about.orEmpty(),
                onValueChange = { viewModel.setDescription(it) },
                label = { Text("Описание") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = uiState.amount,
                onValueChange = { viewModel.setAmount(it) },
                label = { Text("Сумма") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                // Выбор категории
                Button(
                    onClick = {
                            showBottomSheet.value = true
                              },
                    shape = Shapes.small
                ) {
                    Text(text = "Выбрать категорию")
                }

                // Выбор даты
                Button(
                    onClick = { openDateDialog.value = true },
                    shape = Shapes.small
                ) {
                    Text(text = "Выбрать дату")
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    viewModel.addData()
                    navController.popBackStack()
                }
            ) {
                Text(text = "Добавить запись")
            }
        }


    if (showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet.value = false },
            sheetState = sheetState,
        ) {

            CategoriesScreen(
                categories = uiState.categories,
                selectedCategoryId = uiState.selectedCategoryId,
                navController = navController,
                showBottomSheet = showBottomSheet,
                sheetState = sheetState,
                coroutineScope = coroutineScope,
                selectCategory = { viewModel.setCategory(selectedCategoryId = it) },
            )
        }
    }

    if (openDateDialog.value) {
        ShowDatePicker(
            openDateDialog = openDateDialog,
            viewModel = viewModel,
            selectedDate = uiState.selectedDate
        )
    }
}


@Composable
fun ShowDatePicker(
    openDateDialog: MutableState<Boolean>,
    viewModel: AddDataViewModel,
    selectedDate: Long?
) {


    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate
    )
    val confirmEnabled = remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }


    DatePickerDialog(
        onDismissRequest = {
            openDateDialog.value = false
        },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.setDate(datePickerState.selectedDateMillis)
                    openDateDialog.value = false
                },
                enabled = confirmEnabled.value
            ) {
                Text("OK")
            }
        },

        dismissButton = {
            TextButton(
                onClick = {
                    openDateDialog.value = false
                }
            ) {
                Text("Cancel")
            }
        }
    ) {

        DatePicker(state = datePickerState)
    }
}
