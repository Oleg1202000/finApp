package com.mk1morebugs.finapp.ui.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mk1morebugs.finapp.R
import com.mk1morebugs.finapp.ui.Screen
import com.mk1morebugs.finapp.ui.components.FinappNavigationBar
import com.mk1morebugs.finapp.ui.components.FinappScaffold
import com.mk1morebugs.finapp.ui.theme.FinappTheme

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    currentDestination: Screen,
    fromNavBarNavigateTo: (Screen) -> Unit,
) {
    FinappScaffold(
        statusbarTitle = stringResource(R.string.history),
        snackbarHostState = snackbarHostState,
        bottomBar = {
            FinappNavigationBar(
                currentDestination = currentDestination,
                fromNavBarNavigateTo = fromNavBarNavigateTo
            )
        },
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        HistoryScreenContent(
            modifier = Modifier.padding(paddingValues),
            historyGroupByDate = uiState.historyGroupByDate,
            isFactCosts = viewModel::switchTypeCosts,
            deleteCostById = viewModel::deleteCostById,
        )
    }
}

@Composable
fun HistoryScreenContent(
    modifier: Modifier,
    historyGroupByDate: List<HistoryGroupByDate>,
    isFactCosts: (Boolean) -> Unit,
    deleteCostById: (Long) -> Unit,
) {
    var stateTab by remember { mutableIntStateOf(0) }
    val titleResources = listOf(R.string.actually_spent, R.string.planned)

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
    ) {
        item {
            TabRow(selectedTabIndex = stateTab) {
                titleResources.forEachIndexed { index, titleRes ->
                    Tab(
                        selected = stateTab == index,
                        onClick = {
                            stateTab = index
                            isFactCosts(index == 0)
                        },
                        text = {
                            Text(
                                text = stringResource(titleRes),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(15.dp))
        }

        items(historyGroupByDate) { listHistoryItems ->
            Column {
                Text(
                    modifier = Modifier.padding(
                        start = 15.dp,
                        top = 15.dp,
                        bottom = 5.dp,
                    ),
                    text = listHistoryItems.date,
                    style = MaterialTheme.typography.titleLarge
                )

                listHistoryItems.historyItems.forEach { historyItem ->
                    HistoryItemCard(
                        modifier = Modifier.padding(
                            vertical = 5.dp,
                            horizontal = 10.dp
                        ),
                        historyItem = historyItem,
                        deleteSummaryById = {
                            deleteCostById(historyItem.id)
                        }
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun HistoryItemCard(
    modifier: Modifier = Modifier,
    historyItem: HistoryItem,
    deleteSummaryById: () -> Unit,
) {
    var showDropdownMenu by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(75.dp),
        onClick = { showDropdownMenu = true }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.weight(2f),
                painter = painterResource(id = historyItem.iconId),
                contentDescription = null,
                tint = Color(historyItem.iconColor.toULong())
            )
            Column(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .fillMaxHeight()
                    .weight(5f),
            ) {
                if (historyItem.about != null) {
                    Text(
                        modifier = Modifier
                            .padding(top = 5.dp),
                        text = historyItem.about,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                val topPadding = if (historyItem.about != null) 12.dp else 25.dp
                Text(
                    modifier = Modifier
                        .padding(top = topPadding),
                    text = historyItem.categoryName,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            val costValueText = if (historyItem.isIncome) {
                "+${historyItem.amount} ₽"
            } else {
                "-${historyItem.amount} ₽"
            }
            val costValueColor = if (historyItem.isIncome) {
                Color(0xFF14a91a)
            } else {
                Color(0xFFfa0707)
            }
            Text(
                modifier = Modifier.weight(3f),
                text = costValueText,
                style = MaterialTheme.typography.bodyMedium,
                color = costValueColor
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        val showDialog = remember { mutableStateOf(false) }
        DropdownMenu(
            expanded = showDropdownMenu,
            onDismissRequest = { showDropdownMenu = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(R.string.delete))
                },
                onClick = { showDialog.value = true },
                contentPadding = PaddingValues(vertical = 10.dp)
            )
        }
        if (showDialog.value) {
            showDropdownMenu = false
            ConfirmationOfDeletionComposable(
                showDialog = showDialog,
                deleteSummaryById = deleteSummaryById,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConfirmationOfDeletionComposable(
    showDialog: MutableState<Boolean>,
    deleteSummaryById: () -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = { showDialog.value = false }
    ) {
        Surface(
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Вы действительно хотите удалить эту запись?",
                    minLines = 2,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { showDialog.value = false }
                    ) {
                        Text(
                            text = "Нет",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    OutlinedButton(
                        onClick = {
                            deleteSummaryById()
                            showDialog.value = false
                        }
                    ) {
                        Text(
                            text = "Да",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HistoryItemPreview() {
    FinappTheme {
        HistoryItemCard(
            historyItem = HistoryItem(
                id = 0L,
                categoryName = "category name",
                iconId = R.drawable.ic_category_coffee_40px,
                iconColor = Color.Cyan.value.toLong(),
                amount = 1000,
                about = "about description",
                isIncome = false
            ),
            deleteSummaryById = {}
        )
    }
}


@Composable
@Preview(showBackground = true)
fun HistoryContentPreview() {
    FinappTheme {
        HistoryScreenContent(
            modifier = Modifier,
            historyGroupByDate = listOf(
                HistoryGroupByDate(
                    date = "26.10.2024",
                    historyItems = listOf(
                        HistoryItem(
                            id = 0L,
                            categoryName = "category name",
                            iconId = R.drawable.ic_category_coffee_40px,
                            iconColor = Color.Cyan.value.toLong(),
                            amount = 1000,
                            about = "many long about description description",
                            isIncome = false
                        ),
                        HistoryItem(
                            id = 0L,
                            categoryName = "category name 2",
                            iconId = R.drawable.ic_category_coffee_40px,
                            iconColor = Color.Cyan.value.toLong(),
                            amount = 1000,
                            about = null,
                            isIncome = true
                        ),
                        HistoryItem(
                            id = 0L,
                            categoryName = "category name",
                            iconId = R.drawable.ic_category_coffee_40px,
                            iconColor = Color.Cyan.value.toLong(),
                            amount = 1000,
                            about = "about description",
                            isIncome = false
                        )

                    )
                ),
                HistoryGroupByDate(
                    date = "27.10.2024",
                    historyItems = listOf(
                        HistoryItem(
                            id = 0L,
                            categoryName = "category name",
                            iconId = R.drawable.ic_category_coffee_40px,
                            iconColor = Color.Cyan.value.toLong(),
                            amount = 1000,
                            about = "about description",
                            isIncome = false
                        ),
                        HistoryItem(
                            id = 0L,
                            categoryName = "category name 2",
                            iconId = R.drawable.ic_category_coffee_40px,
                            iconColor = Color.Cyan.value.toLong(),
                            amount = 1000,
                            about = null,
                            isIncome = true
                        ),
                        HistoryItem(
                            id = 0L,
                            categoryName = "category name",
                            iconId = R.drawable.ic_category_coffee_40px,
                            iconColor = Color.Cyan.value.toLong(),
                            amount = 1000,
                            about = "about description",
                            isIncome = false
                        )

                    )
                ),
            ),
            isFactCosts = {},
            deleteCostById = {}
        )
    }
}
