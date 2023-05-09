package com.oleg1202000.finapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oleg1202000.finapp.di.LocalRepositoryModule
import com.oleg1202000.finapp.ui.graphdraw.DataGraph
import com.oleg1202000.finapp.ui.graphdraw.GraphPeriod
import com.oleg1202000.finapp.ui.graphdraw.calculateDate
import com.oleg1202000.finapp.ui.theme.defaultColor
import com.oleg1202000.finapp.ui.theme.notOk80Color
import com.oleg1202000.finapp.ui.theme.notOkColor
import com.oleg1202000.finapp.ui.theme.okColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


 @HiltViewModel
class HomeViewModel @Inject constructor(
    private val localRepository: LocalRepositoryModule
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    init {
        getDate(delta = 0)
        updateDataGraph()
    }



     fun updateDataGraph() {
         viewModelScope.launch {


             localRepository.getSumAmount(
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
                                     if (it.plan != null && it.amount / it.plan.toFloat() >= 1) {
                                         notOkColor
                                     } else if (it.plan != null && it.amount / it.plan.toFloat() >= 0.8) {
                                         notOk80Color
                                     } else if (it.plan != null && it.amount / it.plan.toFloat() < 0.8) {
                                         okColor
                                     } else {
                                         defaultColor
                                     },
                                     sumAmount = sumAmount
                                 )
                             }
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


data class HomeUiState(
    val dataGraph: List<DataGraph> = emptyList(),
    val beginDate: Long = 0L,
    val endDate: Long = 0L,
    val selectedGraphPeriod: GraphPeriod = GraphPeriod.WEEK
)
