package com.mk1morebugs.finapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk1morebugs.finapp.di.IRepository
import com.mk1morebugs.finapp.ui.graphdraw.CategoryDetails
import com.mk1morebugs.finapp.ui.graphdraw.GraphPeriod
import com.mk1morebugs.finapp.ui.graphdraw.calculateDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localRepository: IRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getDate(delta = 0)
        updateDataGraph()
    }


    fun updateDataGraph() {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            if (uiState.value.isFactIncome) {

                localRepository.getSumAmount(
                    beginDate = uiState.value.beginDate,
                    endDate = uiState.value.endDate
                )
                    .collect { items ->
                        _uiState.update {
                            it.copy(
                                categoriesDetails = items.map {
                                    CategoryDetails(
                                        categoryName = it.categoryName,
                                        categoryIconId = it.iconId,
                                        categoryIconColor = it.color.toULong(),
                                        categoryAmount = it.amount,
                                    )
                                },
                                sumAmount = items.sumOf { it.amount },
                                isLoading = false
                            )
                        }
                    }
            } else {
                localRepository.getPlan(
                    isIncome = false,
                    beginDate = uiState.value.beginDate,
                    endDate = uiState.value.endDate
                ).collect { items ->
                    _uiState.update { it ->
                        it.copy(
                            categoriesDetails = items.map {
                                CategoryDetails(
                                    categoryName = it.categoryName,
                                    categoryIconId = it.iconId,
                                    categoryIconColor = it.color.toULong(),
                                    categoryAmount = it.plan,
                                )
                            },
                            sumAmount = items.sumOf { it.plan },
                            isLoading = false
                        )
                    }
                }
            }
        }
    }


    fun getDate(
        delta: Int,
        graphPeriod: GraphPeriod = uiState.value.selectedGraphPeriod,
    ) {
        val arrayDate: Array<Long> = calculateDate(delta = delta, graphPeriod = graphPeriod)

        _uiState.update {
            it.copy(
                beginDate = arrayDate[0],
                endDate = arrayDate[1]
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

    fun switchIsFactIncomeValue(
        changeValue: Boolean,
    ) {
        _uiState.update {
            it.copy(
                isFactIncome = changeValue
            )
        }
    }
}

data class HomeUiState(
    val categoriesDetails: List<CategoryDetails> = emptyList(),
    val beginDate: Long = 0L,
    val endDate: Long = 0L,
    val selectedGraphPeriod: GraphPeriod = GraphPeriod.WEEK,
    val isLoading: Boolean = false,
    val isFactIncome: Boolean = true,
    val sumAmount: Int = 0,
)