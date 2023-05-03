package com.oleg1202000.finapp.ui.categories.addcategory

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oleg1202000.finapp.data.Category
import com.oleg1202000.finapp.di.LocalRepositoryModule
import dagger.hilt.android.lifecycle.HiltViewModel
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
                categoryName = categoryName
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


    fun addCategory(
    ) {
        viewModelScope.launch {
            localRepository.setCategory(
                category = Category(
                    //id = 2L,
                    name = uiState.value.categoryName,
                    isIncome = false,
                    color = uiState.value.selectedCategoryColor!!.value.toLong(),
                    iconId = uiState.value.selectedCategoryIcon!!.toInt()
                )
            )
        }
    }
}


data class AddCategoryUiState (
    val categoryName: String = "test",
    val selectedCategoryIcon: Int? = null,
    val selectedCategoryColor: Color? = null,
)