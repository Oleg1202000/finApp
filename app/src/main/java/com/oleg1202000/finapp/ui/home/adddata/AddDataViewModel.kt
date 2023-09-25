package com.oleg1202000.finapp.ui.home.adddata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oleg1202000.finapp.data.Summary
import com.oleg1202000.finapp.di.IRepository
import com.oleg1202000.finapp.ui.categories.CategoryItem
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
class AddDataViewModel @Inject constructor(
    private val localRepository: IRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddDataUiState())
    val uiState: StateFlow<AddDataUiState> = _uiState.asStateFlow()


    init {
        updateData()
    }

    fun updateData() {

        viewModelScope.launch {

            localRepository.getCategories(
                isIncome = uiState.value.isIncome
            )
                .collect { items ->

                    _uiState.update {
                        it.copy(
                            categories = items.map {
                                CategoryItem(
                                    id = it.id,
                                    name = it.name,
                                    IconId = it.iconId,
                                    colorIcon = it.color
                                )
                            }
                        )
                    }
                }
        }
    }


    fun setDescription(
        about: String
    ) {
        _uiState.update {
            it.copy(
                about = about
            )
        }
    }


    fun setAmount(
        amount: String
    ) {
        _uiState.update {
            it.copy(
                amount = calculateAmount(amount = amount),
                errorMessage = null,
                errorTextField = false
            )
        }
    }

    private fun calculateAmount(
        amount: String
    ): String {
        if (amount.isNotEmpty() && amount[amount.length - 1].toString() == "=") {
            val expressionList: List<String> = amount.split(" ")
            if (expressionList.size == 4 && expressionList[1] == "*") {
                return (expressionList[0].toInt() * expressionList[2].toInt()).toString()
            }
        }
        return amount
    }


    fun setDate(
        selectedDate: Long?
    ) {
        _uiState.update {
            it.copy(
                selectedDate = selectedDate
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

    fun setIsIncomeValue(
        changeValue: Boolean
    ) {
        _uiState.update {
            it.copy(
                isIncome = changeValue
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

            } else if (uiState.value.amount.isEmpty()) {
                exceptionMessage = ErrorMessage.AmountIsEmpty
                tempErrorTextField = true

            } else if (!uiState.value.amountIsDigit()) {
                exceptionMessage = ErrorMessage.AmountNotInt
                tempErrorTextField = true

            } else {
                try {
                    localRepository.setSummary(
                        summary = Summary(
                            id = 0L,
                            categoryId = uiState.value.selectedCategoryId!!.toLong(),
                            amount = uiState.value.amount.toInt(),
                            date = uiState.value.selectedDate!!.toLong(),
                            about = uiState.value.about
                        )
                    )
                } catch (e: NumberFormatException) {
                    exceptionMessage = ErrorMessage.AmountOverLimit
                    tempErrorTextField = true
                }
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
}


data class AddDataUiState(
    val categories: List<CategoryItem> = emptyList(),
    val about: String? = null,
    val amount: String = "",
    val selectedCategoryId: Long? = null,
    val selectedDate: Long? = Calendar.getInstance(TimeZone.getDefault()).timeInMillis,
    val errorTextField: Boolean = false,
    val errorMessage: ErrorMessage? = null,
    val isLoading: Boolean = false,
    val isIncome: Boolean = false
)

fun AddDataUiState.amountIsDigit(): Boolean {
    var result = true

    for (char in this.amount) {
        if(!char.isDigit()) {
            result = false
            break
        }
    }
    return result
}
