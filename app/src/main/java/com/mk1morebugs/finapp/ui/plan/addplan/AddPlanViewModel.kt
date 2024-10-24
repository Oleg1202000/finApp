package com.mk1morebugs.finapp.ui.plan.addplan

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk1morebugs.finapp.data.Repository
import com.mk1morebugs.finapp.ui.categories.CategoryItem
import com.mk1morebugs.finapp.ui.home.adddata.ErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar.getInstance
import java.util.TimeZone
import javax.inject.Inject


@HiltViewModel
class AddPlanViewModel @Inject constructor(
    private val localRepository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddPlanUiState())
    val uiState: StateFlow<AddPlanUiState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {

            localRepository.getCategories(
                isIncome = false
            )
                .collect { items ->

                    _uiState.update {
                        it.copy(
                            categories = items.map {
                                CategoryItem(
                                    id = it.id,
                                    name = it.name,
                                    iconId = it.iconId,
                                    colorIcon = it.iconColor
                                )
                            }
                        )
                    }
                }
        }
    }


    fun setAmount(
        amount: String
    ) {
        var tempAmount: String = amount

        if (amount.isNotEmpty() && amount[amount.length - 1].toString() == "=") {
            val expressionList: List<String> = amount.split(" ")
            if (expressionList.size == 4 && expressionList[1] == "*") {
                tempAmount = (expressionList[0].toInt() * expressionList[2].toInt()).toString()
            }
        }

        _uiState.update {
            it.copy(
                amount = tempAmount,
                errorMessage = null,
                errorTextField = false
            )
        }
    }

    fun setDate(
        selectedDate: Long?
    ) {
        _uiState.update {
            it.copy(
                selectedDate = selectedDate ?: 0
            )
        }
    }


    fun setCategory(
        selectedCategoryId: Long
    ) {
        _uiState.update {
            it.copy(
                selectedCategoryId = selectedCategoryId
            )
        }
    }

    fun deleteCategoryById(
        id: Long
    ) {
        _uiState.update {
            it.copy(
                selectedCategoryId = null
            )
        }
        viewModelScope.launch {
            localRepository.deleteCategoryById(
                id = id
            )
        }
    }


    fun addData() {

        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
            var exceptionMessage: ErrorMessage? = null
            var tempErrorTextField = false

            if (uiState.value.selectedCategoryId == null) {
                exceptionMessage = ErrorMessage.CategoryNotSelected

            } /*else if (uiState.value.selectedPeriod == null) {
                exceptionMessage = ErrorMessage.PeriodNotSelected
            }*/ else if (uiState.value.amount.isEmpty()) {
                exceptionMessage = ErrorMessage.AmountIsEmpty
                tempErrorTextField = true

            } else if (!uiState.value.amount.isDigitsOnly()) {
                exceptionMessage = ErrorMessage.AmountNotInt
                tempErrorTextField = true

            } else {
                /*val date: Array<Long> = calculateDate(
                    delta = 0,
                    graphPeriod = uiState.value.selectedPeriod ?: GraphPeriod.WEEK
                )*/

                try {


                } catch (e: NumberFormatException) {
                    exceptionMessage = ErrorMessage.AmountOverLimit
                    tempErrorTextField = true
                }

                //   localRepository.setDatePeriod(uiState.value.selectedPeriod.toString())
                // localRepository.setLastUpdateDate(date[0])

            }

            _uiState.update {
                it.copy(
                    errorMessage = exceptionMessage,
                    errorTextField = tempErrorTextField,
                    isLoading = false
                )
            }
        }
    }


       /*FIXME:
       fun updatePlan() {

        var lastUpdateDate: Long = 0
        var datePeriod: GraphPeriod = GraphPeriod.WEEK

        viewModelScope.launch {
            localRepository.getLastUpdateDate().collect {
                lastUpdateDate = it
            }
            localRepository.getDatePeriod().collect {
                datePeriod = it
            }

            val currentDate: Calendar = getInstance()


            while (true) {

                when (datePeriod) {
                    GraphPeriod.DAY -> {
                        currentDate.add(Calendar.DAY_OF_YEAR, -1)
                        currentDate.set(Calendar.HOUR_OF_DAY, 0)
                        currentDate.set(Calendar.MINUTE, 0)
                        currentDate.set(Calendar.SECOND, 0)
                        currentDate.set(Calendar.MILLISECOND, 0)
                    }

                    GraphPeriod.WEEK -> {
                        currentDate.add(Calendar.WEEK_OF_YEAR, -1)
                        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

                        currentDate.set(Calendar.HOUR_OF_DAY, 0)
                        currentDate.set(Calendar.MINUTE, 0)
                        currentDate.set(Calendar.SECOND, 0)
                        currentDate.set(Calendar.MILLISECOND, 0)

                    }

                    GraphPeriod.MONTH -> {
                        currentDate.add(Calendar.MONTH, -1)
                        currentDate.set(Calendar.HOUR_OF_DAY, 0)
                        currentDate.set(Calendar.MINUTE, 0)
                        currentDate.set(Calendar.SECOND, 0)
                        currentDate.set(Calendar.MILLISECOND, 0)

                    }
                }


                *//*if (lastUpdateDate < currentDate.timeInMillis) {
                    localRepository.setPlan(
                        plan = Planned(

                } else if (lastUpdateDate == currentDate.timeInMillis) {

                } else {
                    break

                }*//*
            }
        }
    }*/
}


data class AddPlanUiState(
    val categories: List<CategoryItem> = emptyList(),
    val amount: String = "",
    val selectedCategoryId: Long? = null,
    val selectedDate: Long = getInstance(TimeZone.getDefault()).timeInMillis,
    //val selectedPeriod: GraphPeriod? = null,
    val errorTextField: Boolean = false,
    val errorMessage: ErrorMessage? = null,
    val isLoading: Boolean = false
)