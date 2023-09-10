package com.oleg1202000.finapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oleg1202000.finapp.di.IRepository
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
class HomeViewModel @Inject constructor(
    private val localRepository: IRepository
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

             localRepository.getSumAmount(
                 isIncome = uiState.value.isIncome,
                 beginDate = uiState.value.beginDate,
                 endDate = uiState.value.endDate
             )
                 .collect { items ->
                     val sumAmount = items.sumOf { it.amount }

                     _uiState.update {
                         it.copy(
                             dataGraph = items.map {
                                 DataGraph(
                                     categoryName = it.categoryName,
                                     iconCategory = it.iconId,
                                     colorIcon = it.color,
                                     amount = it.amount,
                                     coefficientAmount = it.amount/ sumAmount.toFloat(),
                                     colorItem =
                                     if (it.plan != null && it.amount / it.plan.toFloat() > 1) {
                                         ColorGraph.NOT_OK_COLOR
                                     } else if (it.plan != null && it.amount / it.plan.toFloat() >= 0.8) {
                                         ColorGraph.NOT_OK_80_COLOR
                                     } else if (it.plan != null && it.amount / it.plan.toFloat() < 0.8) {
                                         ColorGraph.OK_COLOR
                                     } else {
                                         ColorGraph.DEFAULT_COLOR
                                     },
                                     sumAmount = sumAmount

                                 )

                             },
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

     fun setIsIncomeValue(
         changeValue: Boolean
     ) {
         _uiState.update {
             it.copy(
                 isIncome = changeValue
             )
         }
     }
}


data class HomeUiState(
    val dataGraph: List<DataGraph> = emptyList(),
    val beginDate: Long = 0L,
    val endDate: Long = 0L,
    val selectedGraphPeriod: GraphPeriod = GraphPeriod.WEEK,
    val isLoading: Boolean = false,
    val isIncome: Boolean = false
)
