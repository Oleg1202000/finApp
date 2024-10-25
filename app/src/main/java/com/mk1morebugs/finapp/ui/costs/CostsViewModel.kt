package com.mk1morebugs.finapp.ui.costs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk1morebugs.finapp.data.Repository
import com.mk1morebugs.finapp.data.local.room.CostForUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CostsViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        updateDate(delta = 0)
    }

    fun updateCosts() {
        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            val costs = loadCostsFromDB(
                isFactCosts = uiState.value.isFactCosts
            )
            costs.collect { categoryCost ->
                _uiState.update { homeUiState ->
                    homeUiState.copy(
                        categoriesDetails = categoryCost.map {
                            CategoryDetails(
                                categoryName = it.categoryName,
                                categoryIconId = it.iconId,
                                categoryIconColor = it.iconColor.toULong(),
                                categorySummaryCost = it.summaryAmount,
                            )
                        },
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun loadCostsFromDB(isFactCosts: Boolean): Flow<List<CostForUi>> {
        return repository.getCosts(
            beginDate = uiState.value.beginDate,
            endDate = uiState.value.endDate,
            isPlanned = !isFactCosts,
        )
    }


    fun updateDate(
        delta: Int,
        graphPeriod: GraphPeriod = uiState.value.selectedGraphPeriod,
    ) {
        val arrayBeginAndEndDate: Array<Long> = calculateDate(
            delta = delta,
            graphPeriod = graphPeriod
        )
        _uiState.update {
            it.copy(
                beginDate = arrayBeginAndEndDate[0],
                endDate = arrayBeginAndEndDate[1]
            )
        }
    }

    fun updateGraphPeriod(
        selectedGraphPeriod: GraphPeriod,
    ) {
        _uiState.update {
            it.copy(
                selectedGraphPeriod = selectedGraphPeriod
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
        updateCosts()
    }
}

data class HomeUiState(
    val categoriesDetails: List<CategoryDetails> = emptyList(),
    val beginDate: Long = 0L,
    val endDate: Long = 0L,
    val selectedGraphPeriod: GraphPeriod = GraphPeriod.WEEK,
    val isLoading: Boolean = false,
    val isFactCosts: Boolean = true,
)