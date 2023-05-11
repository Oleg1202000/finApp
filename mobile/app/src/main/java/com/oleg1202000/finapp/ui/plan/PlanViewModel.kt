package com.oleg1202000.finapp.ui.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oleg1202000.finapp.di.LocalRepositoryModule
import com.oleg1202000.finapp.ui.graphdraw.ColorGraph
import com.oleg1202000.finapp.ui.graphdraw.DataGraph
import com.oleg1202000.finapp.ui.graphdraw.GraphPeriod
import com.oleg1202000.finapp.ui.graphdraw.calculateDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlanViewModel @Inject constructor(
    private val localRepository: LocalRepositoryModule
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlanUiState())
    val uiState: StateFlow<PlanUiState> = _uiState.asStateFlow()


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

            localRepository.getPlan(
                isIncome = false,
                beginDate = uiState.value.beginDate,
                endDate = uiState.value.endDate
            )
                .collect { items ->
                    _uiState.update { it ->
                        it.copy(
                            dataGraph = items.map {
                                DataGraph(
                                    categoryName = it.categoryName,
                                    iconCategory = it.iconId,
                                    colorIcon = it.color,
                                    amount = it.amount ?: 0,
                                    coefficientAmount = (it.amount?.toFloat() ?: 0f) / it.plan.toFloat(),
                                    colorItem =
                                    if ((it.amount ?: 0) / it.plan.toFloat() >= 1) {
                                        ColorGraph.NOT_OK_COLOR

                                    } else if ((it.amount ?: 0) / it.plan.toFloat() >= 0.8) {
                                        ColorGraph.NOT_OK_80_COLOR

                                    } else if ((it.amount ?: 0) / it.plan.toFloat() < 0.8) {
                                        ColorGraph.OK_COLOR

                                    } else {
                                        ColorGraph.DEFAULT_COLOR

                                    },
                                    sumAmount = it.plan
                                )

                            },
                            sumPlanned = items.sumOf { it.plan },
                            sumFact = items.sumOf { it.amount ?: 0 },
                            isLoading = false
                        )

                    }
                }
        }

    }


    fun getDate(
        delta: Int,
        graphPeriod: GraphPeriod = uiState.value.selectedGraphPeriod
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
        selectedGraphPeriod: GraphPeriod
    ) {
        _uiState.update {
            it.copy(
                selectedGraphPeriod = selectedGraphPeriod
            )
        }
    }


}


data class PlanUiState(
    val dataGraph: List<DataGraph> = emptyList(),
    val beginDate: Long = 0L,
    val endDate: Long = 0L,
    val selectedGraphPeriod: GraphPeriod = GraphPeriod.WEEK,
    val sumPlanned: Int = 0,
    val sumFact: Int = 0,
    val isLoading: Boolean = false,
)