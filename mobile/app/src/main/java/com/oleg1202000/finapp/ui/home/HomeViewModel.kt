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
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject


 @HiltViewModel
class HomeViewModel  @Inject constructor(
    private val localRepository: LocalRepositoryModule

) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    private val beginDate = MutableStateFlow(Calendar.getInstance().time)
    private val endDate = MutableStateFlow(Calendar.getInstance().time)


    init {
        viewModelScope.launch {
            getDate(deltaWeek = 0)
            localRepository.getSumAmount(
                beginDate = beginDate.value,
                endDate = endDate.value
            )
               // .map { items ->
                .collect { items ->
                    val sumAmount = items.sumOf { it.amount }

                    HomeUiState(
                        dataHome = items.map {
                            DataHome(
                                categoryName = it.categoryName,
                                iconCategory = it.pathToIcon,
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


    fun getDate(deltaWeek: Int) {
        //getYear()
        //getMonth()
        //getYear()

        val currentDate : Calendar = Calendar.getInstance()
        currentDate.set(Calendar.WEEK_OF_YEAR, currentDate.get(Calendar.WEEK_OF_YEAR) + deltaWeek)

        currentDate.set(Calendar.DAY_OF_WEEK, 1)
        beginDate.value = currentDate.time

        currentDate.set(Calendar.DAY_OF_WEEK, 7)
       endDate.value = currentDate.time
    }
}


data class HomeUiState(
    val dataHome: List<DataHome> = emptyList(),
    val sumAmount: Int = 0,
)

data class DataHome(
    val categoryName: String,
    val iconCategory: Int,
    val colorIconCategory: Long,
    val amount: Int,
    val coefficientAmount: Float,
    val colorItem: Color
)
enum class GraphPeriod {
    Day,
    Week,
    Month
}

