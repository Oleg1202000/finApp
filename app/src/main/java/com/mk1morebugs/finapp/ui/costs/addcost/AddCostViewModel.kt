package com.mk1morebugs.finapp.ui.costs.addcost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk1morebugs.finapp.data.Repository
import com.mk1morebugs.finapp.data.local.room.Cost
import com.mk1morebugs.finapp.ui.categories.CategoryItem
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
class AddCostViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddDataUiState())
    val uiState: StateFlow<AddDataUiState> = _uiState.asStateFlow()

    init {
        updateData()
    }

    fun updateData() {
        viewModelScope.launch {
            repository.getCategories(
                isIncome = uiState.value.isIncome
            ).collect { items ->
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

    fun setDescription(
        about: String,
    ) {
        _uiState.update {
            it.copy(
                about = about
            )
        }
    }

    fun setCost(
        amount: String,
    ) {
        _uiState.update {
            it.copy(
                amount = calculateCost(amount = amount),
                isError = false
            )
        }
    }

    private fun calculateCost(
        amount: String,
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
        selectedDate: Long?,
    ) {
        _uiState.update {
            it.copy(
                selectedDate = selectedDate
            )
        }
    }

    fun setCategory(
        selectedCategoryId: Long,
    ) {
        _uiState.update {
            it.copy(
                selectedCategoryId = selectedCategoryId
            )
        }
    }

    fun deleteCategoryById(
        id: Long,
    ) {
        _uiState.update {
            it.copy(
                selectedCategoryId = null
            )
        }

        viewModelScope.launch {
            repository.deleteCategoryById(
                id = id
            )
        }
    }

    fun setIsIncomeValue(
        changeValue: Boolean,
    ) {
        _uiState.update {
            it.copy(
                isIncome = changeValue
            )
        }
    }

    fun addCostToDb(isPlanned: Boolean) {
        _uiState.update {
            it.copy(
                isLoading = true,
                addCostMessageId = null
            )
        }
        viewModelScope.launch {
            var addCostMessageId: Int?
            if (uiState.value.selectedCategoryId == null) {
                addCostMessageId = AddCostMessage.CATEGORY_NOT_SELECTED.stringResource

            } else if (uiState.value.amount.isEmpty()) {
                addCostMessageId = AddCostMessage.AMOUNT_IS_EMPTY.stringResource

            } else if (!uiState.value.amountIsDigit()) {
                addCostMessageId = AddCostMessage.AMOUNT_NOT_INT.stringResource

            } else {
                try {
                    repository.setCost(
                        cost = Cost(
                            id = 0L,
                            categoryId = uiState.value.selectedCategoryId!!.toLong(),
                            amount = uiState.value.amount.toInt(),
                            date = uiState.value.selectedDate!!.toLong(),
                            about = uiState.value.about,
                            isPlanned = isPlanned,
                        )
                    )
                    addCostMessageId = AddCostMessage.OK.stringResource
                } catch (e: NumberFormatException) {
                    addCostMessageId = AddCostMessage.AMOUNT_OVER_LIMIT.stringResource
                }
            }

            _uiState.update {
                it.copy(
                    isError = addCostMessageId != AddCostMessage.OK.stringResource,
                    isLoading = false,
                    addCostMessageId = addCostMessageId
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
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isIncome: Boolean = false,
    val addCostMessageId: Int? = null,
    )

fun AddDataUiState.amountIsDigit(): Boolean {
    var result = true

    for (char in this.amount) {
        if (!char.isDigit()) {
            result = false
            break
        }
    }
    return result
}
