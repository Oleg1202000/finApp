package com.mk1morebugs.finapp.ui.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var stateTab by remember { mutableStateOf(0) }
    val titles = listOf("Фактически \n потрачено", "Запланировано")


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {

        item {
            TabRow(selectedTabIndex = stateTab) {
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


        item {
            Spacer(modifier = Modifier.height(10.dp))
        }


        if (stateTab == 0) {
            viewModel.updateDataSummary()
        } else {
            viewModel.updateDataPlanned()
        }

        items(uiState.historyItems) { item ->
            HistoryItem(
                item = item,
                deleteSummaryById = {
                    if (stateTab == 0) {
                        viewModel.deleteSummaryById(id = item.id)
                    } else {
                        viewModel.deletePlanById(id = item.id)
                    }

                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryItem(
    item: HistoryItem,
    deleteSummaryById: () -> Unit
) {
    var showDropdownMenu by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            onClick = { showDropdownMenu = true }
        ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 15.dp,
                        bottom = 15.dp
                    ),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

                Icon(
                    modifier = Modifier.weight(2f),
                    painter = painterResource(id = item.iconId),
                    contentDescription = null,
                    tint = Color(item.color.toULong())
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(8f)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = format.format(item.date),
                            style = MaterialTheme.typography.bodySmall
                        )

                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "●",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.width(20.dp))

                        if (!item.isIncome) {
                            Text(
                                text = "Расход",
                                style = MaterialTheme.typography.bodySmall
                            )
                        } else {
                            Text(
                                text = "Доход",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    if (item.about != null) {
                        Text(
                            modifier = Modifier.width(140.dp),
                            text = item.about,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Text(
                            modifier = Modifier.weight(6f),
                            text = item.categoryName,
                            style = MaterialTheme.typography.bodyMedium,
                        )

                        Text(
                            modifier = Modifier.weight(4f),
                            text = "${item.amount} ₽",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))


        if (showDropdownMenu) {
            DropdownMenu(
                expanded = true,
                onDismissRequest = { showDropdownMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Удалить") },
                    onClick = { showDialog = true },
                    contentPadding = PaddingValues(vertical = 10.dp)
                )

            }
        }
    }

    if (showDialog) {

        showDropdownMenu = false

        BasicAlertDialog(onDismissRequest = { showDialog = false }
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                elevation = AlertDialogDefaults.TonalElevation
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
                        style = MaterialTheme.typography.headlineMedium

                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = { showDialog = false }
                        ) {
                            Text(
                                text = "Нет",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                        OutlinedButton(
                            onClick = {
                                deleteSummaryById()
                                showDialog = false
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
}