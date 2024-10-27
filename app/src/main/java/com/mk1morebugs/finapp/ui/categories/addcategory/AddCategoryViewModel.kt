package com.mk1morebugs.finapp.ui.categories.addcategory

import android.database.sqlite.SQLiteConstraintException
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk1morebugs.finapp.data.Repository
import com.mk1morebugs.finapp.data.local.room.Category
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
    private val localRepository: Repository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddCategoryUiState())
    val uiState: StateFlow<AddCategoryUiState> = _uiState.asStateFlow()

    fun setCategoryName(
        categoryName: String,
    ) {
        _uiState.update {
            it.copy(
                categoryName = categoryName
            )
        }
    }

    fun setCategoryIcon(
        selectedCategoryIcon: Int,
    ) {
        _uiState.update {
            it.copy(
                selectedCategoryIcon = selectedCategoryIcon
            )
        }
    }

    fun setCategoryColor(
        categoryColor: Color,
    ) {
        _uiState.update {
            it.copy(
                selectedCategoryColor = categoryColor
            )
        }
    }

    fun addCategory() {
        _uiState.update {
            it.copy(
                isLoading = true,
                snackbarMessageId = null
            )
        }
        viewModelScope.launch {
            var snackbarMessageId: Int?
            if (uiState.value.selectedCategoryIcon == null) {
                snackbarMessageId = SnackbarCategoryMessage.IconNotSelected.stringResource

            } else if (uiState.value.selectedCategoryColor == null) {
                snackbarMessageId = SnackbarCategoryMessage.ColorNotSelected.stringResource

            } else {

                try {
                    localRepository.setCategory(
                        category = Category(
                            name = uiState.value.categoryName,
                            isIncome = false,
                            color = uiState.value.selectedCategoryColor!!.value.toLong(),
                            iconId = uiState.value.selectedCategoryIcon!!.toInt()
                        )
                    )
                    delay(500)
                    snackbarMessageId = SnackbarCategoryMessage.OK.stringResource

                } catch (e: SQLiteConstraintException) {
                    snackbarMessageId = SnackbarCategoryMessage.NameNotUnique.stringResource
                }
            }

            _uiState.update {
                it.copy(
                    isBackToPreviousScreen = snackbarMessageId == SnackbarCategoryMessage.OK.stringResource,
                    snackbarMessageId = snackbarMessageId,
                    isLoading = false
                )
            }
        }
    }
}

data class AddCategoryUiState(
    val categoryName: String = "",
    val selectedCategoryIcon: Int? = null,
    val selectedCategoryColor: Color? = null,
    val isLoading: Boolean = false,
    val isBackToPreviousScreen: Boolean = false,
    val snackbarMessageId: Int? = null,
)
