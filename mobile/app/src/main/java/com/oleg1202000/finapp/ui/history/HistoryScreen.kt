package com.oleg1202000.finapp.ui.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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


    LazyColumn {

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


        if (stateTab == 0) {
            viewModel.updateDataSummary()
        } else {
            viewModel.updateDataPlanned()
        }

        items(uiState.historyItems) { item ->
            HistoryItem(item = item)
        }
    }
}


@Composable
fun HistoryItem(
    item: HistoryItem
) {
    Card {
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

                Row {
                    Text(
                        text = format.format(item.date),
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    if (item.about != null) {
                        Text(
                            text = item.about,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Start
                ) {

                    Text(
                        text = item.categoryName,
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Spacer(modifier = Modifier.width(30.dp))

                    Text(
                        text = "- ${item.amount} ₽",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}