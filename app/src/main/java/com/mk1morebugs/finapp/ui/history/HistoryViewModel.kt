package com.mk1morebugs.finapp.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk1morebugs.finapp.data.Repository
import com.mk1morebugs.finapp.data.local.room.CostHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        loadCostHistory()
    }

    fun loadCostHistory() {
        viewModelScope.launch {
            val costHistory = loadCostsHistoryFromDB(isFactCosts = uiState.value.isFactCosts)

            costHistory.collect { listOfCosts ->
                val historyGroupByDate = getHistoryGroupByDate(
                    listOfCosts = listOfCosts
                )
                _uiState.update { historyUiState ->
                    historyUiState.copy(
                        historyGroupByDate = historyGroupByDate
                    )
                }
            }
        }
    }

    private fun loadCostsHistoryFromDB(isFactCosts: Boolean): Flow<List<CostHistory>> {
        val today = Calendar.getInstance().timeInMillis
        return repository.getCostsHistory(
            beginDate = 0L,
            endDate = today,
            isPlanned = !isFactCosts,
        )
    }

    private fun formatDateToStringDdMmYyyyFormat(dateInMillis: Long): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return dateFormat.format(dateInMillis)
    }

    private fun getHistoryGroupByDate(listOfCosts: List<CostHistory>): List<HistoryGroupByDate> {
        val historyGroupByDate: MutableList<HistoryGroupByDate> = mutableListOf()
        val historyItems: MutableList<HistoryItem> = mutableListOf()
        var historyDate = formatDateToStringDdMmYyyyFormat(
            dateInMillis = listOfCosts.firstOrNull()?.date ?: 0L
        )
        for (itemOfCosts in listOfCosts) {
            val historyDateCurrent = formatDateToStringDdMmYyyyFormat(
                dateInMillis = itemOfCosts.date
            )
            fun addHistoryItemsToResultList() {
                historyGroupByDate.add(
                    HistoryGroupByDate(date = historyDate, historyItems = historyItems.toList())
                )
                historyDate = historyDateCurrent
                historyItems.clear()
            }

            if (historyDate != historyDateCurrent) {
                addHistoryItemsToResultList()
            }
            historyItems.add(
                HistoryItem(
                    id = itemOfCosts.id,
                    categoryName = itemOfCosts.categoryName,
                    iconId = itemOfCosts.iconId,
                    iconColor = itemOfCosts.iconColor,
                    amount = itemOfCosts.amount,
                    about = itemOfCosts.about,
                    isIncome = itemOfCosts.isIncome
                )
            )
            if (itemOfCosts == listOfCosts.last()) {
                addHistoryItemsToResultList()
            }
        }
        return historyGroupByDate
    }

    fun deleteCostById(id: Long) {
        viewModelScope.launch {
            repository.deleteCostById(
                id = id
            )
        }
    }

    fun switchTypeCosts(
        isFactCosts: Boolean,
    ) {
        _uiState.update {
            it.copy(
                isFactCosts = isFactCosts
            )
        }
        loadCostHistory()
    }
}

data class HistoryUiState(
    val historyGroupByDate: List<HistoryGroupByDate> = listOf(),
    val isFactCosts: Boolean = true,
)

data class HistoryGroupByDate(
    val historyItems: List<HistoryItem> = listOf(),
    val date: String = "",
)

data class HistoryItem(
    val id: Long,
    val categoryName: String,
    val iconId: Int,
    val iconColor: Long,
    val amount: Int,
    val about: String?,
    val isIncome: Boolean,
)