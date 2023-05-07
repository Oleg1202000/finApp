package com.oleg1202000.finapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oleg1202000.finapp.di.LocalRepositoryModule
import com.oleg1202000.finapp.ui.graphdraw.DataGraph
import com.oleg1202000.finapp.ui.graphdraw.GraphPeriod
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
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject


 @HiltViewModel
class HomeViewModel  @Inject constructor(
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
                                     if (it.plan != null && it.amount / it.plan.toFloat() > 1) {
                                         notOkColor
                                     } else if (it.plan != null && it.amount / it.plan.toFloat() >= 0.8) {
                                         notOk80Color
                                     } else if (it.plan != null && it.amount / it.plan.toFloat() < 0.8) {
                                         okColor
                                     } else {
                                         defaultColor
                                     },
                                     sumAmount = sumAmount                                 )
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

        val beginDate: Long
        val endDate: Long
        val currentDate : Calendar = Calendar.getInstance(TimeZone.getDefault())
            // currentDate.get (Calendar.MONDAY)


        when (graphPeriod) {

            GraphPeriod.DAY -> {
                currentDate.add(Calendar.DAY_OF_YEAR, delta)

                currentDate.set(Calendar.HOUR_OF_DAY, 0)
                currentDate.set(Calendar.MINUTE, 0)
                currentDate.set(Calendar.SECOND, 0)
                currentDate.set(Calendar.MILLISECOND, 0)
                beginDate = currentDate.timeInMillis


                currentDate.set(Calendar.HOUR_OF_DAY, 23)
                currentDate.set(Calendar.MINUTE, 59)
                currentDate.set(Calendar.SECOND, 59)
                currentDate.set(Calendar.MILLISECOND, 999)
                endDate = currentDate.timeInMillis
            }

            GraphPeriod.Week -> {
                currentDate.add(Calendar.WEEK_OF_YEAR, delta)

                currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                currentDate.set(Calendar.HOUR_OF_DAY, 0)
                currentDate.set(Calendar.MINUTE, 0)
                currentDate.set(Calendar.SECOND, 0)
                currentDate.set(Calendar.MILLISECOND, 0)
                beginDate = currentDate.timeInMillis

                currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
                currentDate.set(Calendar.HOUR_OF_DAY, 23)
                currentDate.set(Calendar.MINUTE, 59)
                currentDate.set(Calendar.SECOND, 59)
                currentDate.set(Calendar.MILLISECOND, 999)
                endDate = currentDate.timeInMillis

            }

            GraphPeriod.Month -> {
                currentDate.add(Calendar.MONTH, delta)

                currentDate.set(Calendar.DAY_OF_MONTH, 1)
                currentDate.set(Calendar.HOUR_OF_DAY, 0)
                currentDate.set(Calendar.MINUTE, 0)
                currentDate.set(Calendar.SECOND, 0)
                currentDate.set(Calendar.MILLISECOND, 0)
                beginDate = currentDate.timeInMillis


                currentDate.set(Calendar.DAY_OF_MONTH, currentDate.getActualMaximum(Calendar.DAY_OF_MONTH))
                currentDate.set(Calendar.HOUR_OF_DAY, 23)
                currentDate.set(Calendar.MINUTE, 59)
                currentDate.set(Calendar.SECOND, 59)
                currentDate.set(Calendar.MILLISECOND, 999)
                endDate = currentDate.timeInMillis
            }
        }



        _uiState.update {
            it.copy(
                beginDate = beginDate,
                endDate = endDate
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
    val selectedGraphPeriod: GraphPeriod = GraphPeriod.Week
)
