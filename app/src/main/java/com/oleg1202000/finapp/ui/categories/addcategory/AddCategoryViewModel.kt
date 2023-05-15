package com.oleg1202000.finapp.ui.categories.addcategory

import android.database.sqlite.SQLiteConstraintException
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oleg1202000.finapp.data.Category
import com.oleg1202000.finapp.di.LocalRepositoryModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val localRepository: LocalRepositoryModule
) : ViewModel() {


    private val _uiState = MutableStateFlow(AddCategoryUiState())
    val uiState: StateFlow<AddCategoryUiState> = _uiState.asStateFlow()


    fun setCategoryName(
        categoryName: String
    ) {
        _uiState.update {
            it.copy(
                categoryName = categoryName.trim()
            )
        }
    }


    fun setCategoryIcon(
        selectedCategoryIcon: Int
    ) {
        _uiState.update {
            it.copy(
                selectedCategoryIcon = selectedCategoryIcon
            )
        }
    }


    fun setCategoryColor(
        CategoryColor: Color
    ) {
        _uiState.update {
            it.copy(
                selectedCategoryColor = CategoryColor
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


    fun addCategory(
    ) {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        var exceptionMessage: ErrorCategoryMessage? = null
        viewModelScope.launch {
        if (uiState.value.selectedCategoryIcon == null) {
            exceptionMessage = ErrorCategoryMessage.IconNotSelected

        } else if (uiState.value.selectedCategoryColor == null) {
            exceptionMessage = ErrorCategoryMessage.ColorNotSelected

        } else {
                try {
                    localRepository.setCategory(
                        category = Category(
                            name = uiState.value.categoryName,
                            isIncome = uiState.value.isIncome,
                            color = uiState.value.selectedCategoryColor!!.value.toLong(),
                            iconId = uiState.value.selectedCategoryIcon!!.toInt()
                        )
                    )
                } catch (e: SQLiteConstraintException) {
                    exceptionMessage = ErrorCategoryMessage.NameNotUnique
                }
            }
            delay(1000)

        _uiState.update {
            it.copy(
                errorCategoryMessage = exceptionMessage,
                isLoading = false
            )
        }
        }
    }
}


data class AddCategoryUiState (
    val categoryName: String = "",
    val selectedCategoryIcon: Int? = null,
    val selectedCategoryColor: Color? = null,
    val errorCategoryMessage: ErrorCategoryMessage? = null,
    val isLoading: Boolean = false,
    val isIncome: Boolean = false
)
