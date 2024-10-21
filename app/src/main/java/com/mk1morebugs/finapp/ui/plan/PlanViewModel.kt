package com.mk1morebugs.finapp.ui.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk1morebugs.finapp.data.IRepository
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
class PlanViewModel @Inject constructor(
    private val localRepository: IRepository
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
    val detailData: List<CategoryDetails> = emptyList(),
    val beginDate: Long = 0L,
    val endDate: Long = 0L,
    val selectedGraphPeriod: GraphPeriod = GraphPeriod.WEEK,
    val sumPlanned: Int = 0,
    val sumFact: Int = 0,
    val isLoading: Boolean = false,
)