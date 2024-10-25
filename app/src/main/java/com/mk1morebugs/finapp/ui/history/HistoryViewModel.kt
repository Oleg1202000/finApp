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
import java.util.Calendar
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

    private fun loadCostHistory() {
        viewModelScope.launch {
            val costHistory = loadCostsHistoryFromDB(isFactCosts = uiState.value.isFactCosts)

            costHistory.collect { costItem ->
                _uiState.update { historyUiState ->
                    historyUiState.copy(
                        historyItems = costItem.map {
                            HistoryItem(
                                id = it.id,
                                categoryName = it.categoryName,
                                iconId = it.iconId,
                                color = it.iconColor,
                                amount = it.amount,
                                date = it.date,
                                about = it.about,
                                isIncome = it.isIncome
                            )
                        }
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
    val historyItems: List<HistoryItem> = emptyList(),
    val isFactCosts: Boolean = true,
)

data class HistoryItem(
    val id: Long,
    val categoryName: String,
    val iconId: Int,
    val color: Long,
    val amount: Int,
    val date: Long,
    val about: String?,
    val isIncome: Boolean,
)