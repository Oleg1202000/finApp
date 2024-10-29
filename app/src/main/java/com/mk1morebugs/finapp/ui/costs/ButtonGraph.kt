package com.mk1morebugs.finapp.ui.costs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun ButtonGraph(
    modifier: Modifier = Modifier,
    beginDate: Long,
    endDate: Long,
    updateDate: (Int) -> Unit,
    updateDataGraph: () -> Unit,
    updateGraphPeriod: (GraphPeriod) -> Unit,
) {
    val delta: MutableState<Int> = remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val format = SimpleDateFormat("dd.MM", Locale.getDefault())

            OutlinedButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                onClick = {
                    delta.value -= 1
                    updateDate(delta.value)
                    updateDataGraph()
                }
            ) {
                Text(
                    text = "<"
                )
            }
            Spacer(modifier = Modifier.width(20.dp))

            Text(text = "${format.format(beginDate)} - ${format.format(endDate)}")

            Spacer(modifier = Modifier.width(20.dp))
            OutlinedButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                onClick = {
                    delta.value += 1
                    updateDate(delta.value)
                    updateDataGraph()
                }
            ) {
                Text(
                    text = ">"
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))

        // Кнопки День / неделя / месяц
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ElevatedButton(
                onClick = {
                    delta.value = 0
                    updateGraphPeriod(GraphPeriod.DAY)
                    updateDate(delta.value)
                    updateDataGraph()
                }
            ) {
                Text(text = "День")
            }

            ElevatedButton(
                onClick = {
                    delta.value = 0
                    updateGraphPeriod(GraphPeriod.WEEK)
                    updateDate(delta.value)
                    updateDataGraph()
                }
            ) {
                Text(text = "Неделя")
            }

            ElevatedButton(
                onClick = {
                    delta.value = 0
                    updateGraphPeriod(GraphPeriod.MONTH)
                    updateDate(delta.value)
                    updateDataGraph()
                }
            ) {
                Text(text = "Месяц")
            }
        }
    }
}