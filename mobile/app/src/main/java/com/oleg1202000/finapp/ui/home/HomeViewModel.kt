package com.oleg1202000.finapp.ui.home

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oleg1202000.finapp.di.LocalRepositoryModule
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
                                     colorIconCategory = it.color,
                                     amount = it.amount,
                                     coefficientAmount = it.amount.toFloat() / sumAmount.toFloat(),
                                     colorItem =
                                     if (it.plan != null && it.amount.toFloat() / it.plan.toFloat() > 1) {
                                         notOkColor
                                     } else if (it.plan != null && it.amount.toFloat() / it.plan.toFloat() >= 0.8) {
                                         notOk80Color
                                     } else if (it.plan != null && it.amount.toFloat() / it.plan.toFloat() < 0.8) {
                                         okColor
                                     } else {
                                         defaultColor
                                     }
                                 )
                             },
                             sumAmount = sumAmount,
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


        val currentDate : Calendar = Calendar.getInstance()

        when (graphPeriod) {

            GraphPeriod.DAY -> {
                currentDate.set(Calendar.DAY_OF_YEAR, currentDate.get(Calendar.DAY_OF_YEAR) + delta)
                currentDate.set(Calendar.HOUR_OF_DAY, 0)
                beginDate = currentDate.timeInMillis

                currentDate.set(Calendar.DAY_OF_YEAR, currentDate.get(Calendar.DAY_OF_YEAR) + delta)
                currentDate.set(Calendar.HOUR_OF_DAY, 24)
                endDate = currentDate.timeInMillis
            }

            GraphPeriod.Week -> {
                currentDate.set(Calendar.WEEK_OF_YEAR, currentDate.get(Calendar.WEEK_OF_YEAR) + delta)


                currentDate.set(Calendar.DAY_OF_WEEK, 2)
                beginDate = currentDate.timeInMillis


                currentDate.set(Calendar.DAY_OF_WEEK, 1)
                endDate = currentDate.timeInMillis

            }

            GraphPeriod.Month -> {
                currentDate.set(Calendar.MONTH, currentDate.get(Calendar.MONTH) + delta)


                currentDate.set(Calendar.DAY_OF_MONTH, 1)
                beginDate = currentDate.timeInMillis


                currentDate.set(Calendar.DAY_OF_MONTH, currentDate.getActualMaximum(Calendar.DAY_OF_MONTH))
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
    val sumAmount: Int = 0,
    val beginDate: Long = 0L,
    val endDate: Long = 0L,
    val selectedGraphPeriod: GraphPeriod = GraphPeriod.Week
)

enum class GraphPeriod {
    DAY,
    Week,
    Month
}

data class DataGraph(
    val categoryName: String,
    val iconCategory: Int,
    val colorIconCategory: Long,
    val amount: Int,
    val coefficientAmount: Float,
    val colorItem: Color
)




