package com.mk1morebugs.finapp.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk1morebugs.finapp.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val localRepository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()


    init {
        updateDataSummary()
    }


    fun updateDataSummary() {

        viewModelScope.launch {
            localRepository.getSummaryHistory(
                beginDate = Calendar.getInstance().timeInMillis,
                endDate = 0L
            )
                .collect { items ->
                    _uiState.update {
                        it.copy(
                            historyItems = items.map {
                                HistoryItem(
                                    id = it.id,
                                    categoryName = it.categoryName,
                                    iconId = it.iconId,
                                    color = it.color,
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

    fun updateDataPlanned() {

        viewModelScope.launch {
            localRepository.getPlannedHistory(
                beginDate = Calendar.getInstance().timeInMillis,
                endDate = 0L
            )
                .collect { items ->
                    _uiState.update {
                        it.copy(
                            historyItems = items.map {
                                HistoryItem(
                                    id = it.id,
                                    categoryName = it.categoryName,
                                    iconId = it.iconId,
                                    color = it.color,
                                    amount = it.amount,
                                    date = it.date,
                                    about = null,
                                    isIncome = false
                                )
                            }
                        )
                    }
                }
        }
    }


    fun deleteSummaryById(
        id: Long
    ) {

        viewModelScope.launch {
            localRepository.deleteSummaryById(
                id = id
            )
        }
    }

    fun deletePlanById(
        id: Long
    ) {

        viewModelScope.launch {
            localRepository.deletePlanById(
                id = id
            )
        }
    }
}


data class HistoryUiState(
    val historyItems: List<HistoryItem> = emptyList(),
)


data class HistoryItem(
    val id: Long,
    val categoryName: String,
    val iconId: Int,
    val color: Long,
    val amount: Int,
    val date: Long,
    val about: String?,
    val isIncome: Boolean
)
