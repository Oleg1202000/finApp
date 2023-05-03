package com.oleg1202000.finapp.ui.home.adddata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oleg1202000.finapp.data.Summary
import com.oleg1202000.finapp.di.LocalRepositoryModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject


@HiltViewModel
class AddDataViewModel  @Inject constructor(
    private val localRepository: LocalRepositoryModule
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddDataUiState())
    val uiState: StateFlow<AddDataUiState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {

            localRepository.getCategries(
                isIncome = false
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
                amount = amount
            )
        }
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


    fun addData() {
        if (uiState.value.selectedCategoryId == null) {
            // TODO: Вывести сообщение об ошибке
        }

        viewModelScope.launch {

            localRepository.setSummary(
                summary = Summary(
                    id = 0L,
                    categoryId = uiState.value.selectedCategoryId!!.toLong(),
                    amount = uiState.value.amount.toInt(),
                    date = uiState.value.selectedDate!!.toLong(),
                    about = uiState.value.about
                )
            )
        }
    }
}


data class CategoryItem(
    val id: Long,
    val name: String,
    val IconId: Int,
    val colorIcon: Long,
)

data class AddDataUiState(
    val categories: List<CategoryItem> = emptyList(),
    val about: String? = null,
    val amount: String = "",
    val selectedCategoryId: Long? = null,
    val selectedDate: Long? = Calendar.getInstance().timeInMillis,
)
