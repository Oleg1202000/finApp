package com.mk1morebugs.finapp.ui.costs.addcost

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mk1morebugs.finapp.R
import com.mk1morebugs.finapp.ui.Screen
import com.mk1morebugs.finapp.ui.categories.CategoriesScreen
import com.mk1morebugs.finapp.ui.categories.CategoryItem
import com.mk1morebugs.finapp.ui.components.FinappNavigationBar
import com.mk1morebugs.finapp.ui.components.FinappScaffold
import com.mk1morebugs.finapp.ui.theme.Shapes
import kotlinx.coroutines.CoroutineScope

@Composable
fun AddCostScreen(
    viewModel: AddCostViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    currentDestination: Screen,
    fromNavBarNavigateTo: (Screen) -> Unit,
    backToPreviousDestination: () -> Unit,
    navigateTo: (Screen) -> Unit,
    isPlanned: Boolean,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    uiState.addCostMessageId?.let { messageId ->
        val message = stringResource(id = messageId)
        LaunchedEffect(messageId) {
            snackbarHostState.showSnackbar(
                message = message
            )
        }
    }

    FinappScaffold(
        statusbarTitle = stringResource(R.string.add_cost),
        snackbarHostState = snackbarHostState,
        bottomBar = {
            FinappNavigationBar(
                currentDestination = currentDestination,
                fromNavBarNavigateTo = fromNavBarNavigateTo
            )
        },
    ) { paddingValues ->
        AddCostScreenContent(
            modifier = Modifier
                .padding(paddingValues),
            coroutineScope = coroutineScope,
            backToPreviousDestination = backToPreviousDestination,
            navigateTo = navigateTo,
            isPlanned = isPlanned,
            aboutValue = uiState.about,
            onAboutValueChange = viewModel::setDescription,
            amountValue = uiState.amount,
            onAmountValueChange = viewModel::setCost,
            isError = uiState.isError,
            addCostMessageId = uiState.addCostMessageId,

            onAddCostClick = viewModel::addCostToDb,
            uiStateIsLoading = uiState.isLoading,

            categories = uiState.categories,
            selectedCategoryId = uiState.selectedCategoryId,
            setCategory = viewModel::setCategory,
            deleteCategoryById = viewModel::deleteCategoryById,
            selectedDate = uiState.selectedDate,
            setDate = viewModel::setDate,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCostScreenContent(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope,
    backToPreviousDestination: () -> Unit,
    navigateTo: (Screen) -> Unit,
    isPlanned: Boolean,
    aboutValue: String?,
    onAboutValueChange: (String) -> Unit,
    amountValue: String,
    onAmountValueChange: (String) -> Unit,
    isError: Boolean,
    addCostMessageId: Int?,
    onAddCostClick: (Boolean) -> Unit,
    uiStateIsLoading: Boolean,
    categories: List<CategoryItem>,
    selectedCategoryId: Long?,
    setCategory: (Long) -> Unit,
    deleteCategoryById: (Long) -> Unit,
    selectedDate: Long?,
    setDate: (Long?) -> Unit,
) {
    val openDateDialog = rememberSaveable { mutableStateOf(false) }
    val showBottomSheet = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(150.dp))

        OutlinedTextField(
            value = aboutValue.orEmpty(),
            onValueChange = onAboutValueChange,
            label = { Text("Описание") }
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = amountValue,
            singleLine = true,
            onValueChange = onAmountValueChange,
            label = { Text("Сумма, ₽") },
            supportingText = {
                Text(
                    text = stringResource(
                        addCostMessageId ?: R.string.add_cost_supporting_text
                    )
                )
            },
            isError = isError
        )
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    showBottomSheet.value = true
                },
                shape = Shapes.small
            ) {
                Text(text = "Выбрать категорию")
            }

            Button(
                onClick = { openDateDialog.value = true },
                shape = Shapes.small
            ) {
                Text(text = "Выбрать дату")
            }
        }
        Spacer(modifier = Modifier.height(30.dp))

        var showAddResult by rememberSaveable { mutableStateOf(false) }
        Button(
            onClick = {
                onAddCostClick(isPlanned)
                showAddResult = true
            }
        ) {
            Text(text = "Добавить запись")
        }

        if (showAddResult && uiStateIsLoading) {
            CircularProgressIndicator()

        } else if (showAddResult && !isError) {
            backToPreviousDestination()
            Log.d("backToPreviousDestination", true.toString())
        }
    }

    val sheetState = rememberModalBottomSheetState()
    if (showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet.value = false },
            sheetState = sheetState,
        ) {
            CategoriesScreen(
                categories = categories,
                selectedCategoryId = selectedCategoryId,
                showBottomSheet = showBottomSheet,
                sheetState = sheetState,
                coroutineScope = coroutineScope,
                selectCategory = setCategory,
                deleteCategoryById = deleteCategoryById,
                navigateTo = navigateTo
            )
        }
    }

    if (openDateDialog.value) {
        ShowDatePicker(
            openDateDialog = openDateDialog,
            setDate = setDate,
            selectedDate = selectedDate
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDatePicker(
    openDateDialog: MutableState<Boolean>,
    setDate: (Long?) -> Unit,
    selectedDate: Long?,
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
                    setDate(datePickerState.selectedDateMillis)
                    openDateDialog.value = false
                },
                enabled = confirmEnabled.value
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { openDateDialog.value = false }
            ) {
                Text("Отмена")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
