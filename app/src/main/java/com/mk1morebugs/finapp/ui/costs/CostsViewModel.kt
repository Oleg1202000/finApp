package com.mk1morebugs.finapp.ui.costs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk1morebugs.finapp.R
import com.mk1morebugs.finapp.data.Repository
import com.mk1morebugs.finapp.data.local.room.Category
import com.mk1morebugs.finapp.data.local.room.CostForUi
import com.mk1morebugs.finapp.ui.theme.colorCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CostsViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        checkFirstLaunch()
        updateDate(delta = 0)
    }

    fun updateCosts() {
        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            val costs = loadCostsFromDB(
                isFactCosts = uiState.value.isFactCosts
            )
            costs.collect { categoryCost ->
                _uiState.update { homeUiState ->
                    homeUiState.copy(
                        categoriesDetails = categoryCost.map {
                            CategoryDetails(
                                categoryName = it.categoryName,
                                categoryIconId = it.iconId,
                                categoryIconColor = it.iconColor.toULong(),
                                categorySummaryCost = it.summaryAmount,
                            )
                        },
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun loadCostsFromDB(isFactCosts: Boolean): Flow<List<CostForUi>> {
        return repository.getCosts(
            beginDate = uiState.value.beginDate,
            endDate = uiState.value.endDate,
            isPlanned = !isFactCosts,
        )
    }


    fun updateDate(
        delta: Int,
        graphPeriod: GraphPeriod = uiState.value.selectedGraphPeriod,
    ) {
        val arrayBeginAndEndDate: Array<Long> = calculateDate(
            delta = delta,
            graphPeriod = graphPeriod
        )
        _uiState.update {
            it.copy(
                beginDate = arrayBeginAndEndDate[0],
                endDate = arrayBeginAndEndDate[1]
            )
        }
    }

    fun updateGraphPeriod(
        selectedGraphPeriod: GraphPeriod,
    ) {
        _uiState.update {
            it.copy(
                selectedGraphPeriod = selectedGraphPeriod
            )
        }
    }

    fun switchTypeCosts(
        isFactCosts: Boolean,
    ) {
        _uiState.update {
            it.copy(
                isFactCosts = isFactCosts
            )
        }
        updateCosts()
    }

    private fun checkFirstLaunch() {
        viewModelScope.launch {
            val isFirstLaunch = repository.getIsFirstLaunch()

            if (isFirstLaunch) {
                initCategoriesInFirstLaunch()
                repository.setIsFirstLaunch(false)
            }
        }
    }

    private fun initCategoriesInFirstLaunch() {
        val categoriesTemplate = listOf(
            Category(
                id = 0L,
                name = "Супермаркеты",
                isIncome = false,
                color = colorCategories[2].value.toLong(),
                iconId = R.drawable.ic_category_shopping_cart_40px,
            ),
            Category(
                id = 0L,
                name = "Фастфуд",
                isIncome = false,
                color = colorCategories[4].value.toLong(),
                iconId = R.drawable.ic_category_fastfood_40px,
            ),
            Category(
                id = 0L,
                name = "Кофейни",
                isIncome = false,
                color = colorCategories[32].value.toLong(),
                iconId = R.drawable.ic_category_coffee_40px,
            ),
            Category(
                id = 0L,
                name = "Развлечения",
                isIncome = false,
                color = colorCategories[20].value.toLong(),
                iconId = R.drawable.ic_category_theater_comedy_40px,
            ),
            Category(
                id = 0L,
                name = "Дом",
                isIncome = false,
                color = colorCategories[26].value.toLong(),
                iconId = R.drawable.ic_category_table_lamp_40px,
            ),
            Category(
                id = 0L,
                name = "Книги",
                isIncome = false,
                color = colorCategories[28].value.toLong(),
                iconId = R.drawable.ic_category_devices_40px,
            ),
            Category(
                id = 0L,
                name = "одежда и обувь",
                isIncome = false,
                color = colorCategories[14].value.toLong(),
                iconId = R.drawable.ic_category_checkroom_40px,
            ),
            Category(
                id = 0L,
                name = "Транспорт",
                isIncome = false,
                color = colorCategories[16].value.toLong(),
                iconId = R.drawable.ic_category_directions_bus_40px,
            ),
            Category(
                id = 0L,
                name = "Здоровье",
                isIncome = false,
                color = colorCategories[8].value.toLong(),
                iconId = R.drawable.ic_category_local_hospital_40px,
            ),
            Category(
                id = 0L,
                name = "Подписки на цифровые услуги",
                isIncome = false,
                color = colorCategories[10].value.toLong(),
                iconId = R.drawable.ic_category_cottage_40px,
            ),
            Category(
                id = 0L,
                name = "Оплата связи",
                isIncome = false,
                color = colorCategories[12].value.toLong(),
                iconId = R.drawable.ic_category_cell_tower_40px,
            ),
            Category(
                id = 0L,
                name = "Образование",
                isIncome = false,
                color = colorCategories[18].value.toLong(),
                iconId = R.drawable.ic_category_school_40px,
            ),
            Category(
                id = 0L,
                name = "Авиабилеты",
                isIncome = false,
                color = colorCategories[22].value.toLong(),
                iconId = R.drawable.ic_category_flight_takeoff_40px,
            ),
            Category(
                id = 0L,
                name = "ЖКХ",
                isIncome = false,
                color = colorCategories[0].value.toLong(),
                iconId = R.drawable.ic_category_cottage_40px,
            ),
            Category(
                id = 0L,
                name = "Парикмахерские",
                isIncome = false,
                color = colorCategories[6].value.toLong(),
                iconId = R.drawable.ic_category_cut_40px,
            ),
            Category(
                id = 0L,
                name = "Цветы",
                isIncome = false,
                color = colorCategories[24].value.toLong(),
                iconId = R.drawable.ic_category_fertile_40px,
            ),
            Category(
                id = 0L,
                name = "Cпорт и фитнес",
                isIncome = false,
                color = colorCategories[30].value.toLong(),
                iconId = R.drawable.ic_category_stadium_40px,
            ),
        )
        viewModelScope.launch {
            for (category in categoriesTemplate) {
                repository.setCategory(category)
            }
        }
    }
}

data class HomeUiState(
    val categoriesDetails: List<CategoryDetails> = emptyList(),
    val beginDate: Long = 0L,
    val endDate: Long = 0L,
    val selectedGraphPeriod: GraphPeriod = GraphPeriod.WEEK,
    val isLoading: Boolean = false,
    val isFactCosts: Boolean = true,
)