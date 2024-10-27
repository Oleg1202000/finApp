package com.mk1morebugs.finapp.ui.costs.addcost

import com.google.common.truth.Truth.assertThat
import com.mk1morebugs.finapp.data.FakeRepository
import com.mk1morebugs.finapp.data.local.room.Cost
import com.mk1morebugs.finapp.data.fakeCategories
import com.mk1morebugs.finapp.data.fakeDeletedCategory
import com.mk1morebugs.finapp.ui.categories.CategoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddCostToDbViewModelTest {
    private lateinit var repository: FakeRepository
    private lateinit var viewModel: AddCostViewModel

    @Before
    fun setUp() = runTest {
        val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)

        repository = FakeRepository()

        launch {
            fakeCategories.forEach {
                repository.setCategory(it)
            }
        }
        advanceUntilIdle()

        viewModel = AddCostViewModel(repository = repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun updateCategoriesData_returnListCategoryItem() = runTest {
        val expectedCategoriesList =  fakeCategories.map {
            CategoryItem(
                id = it.id,
                name = it.name,
                colorIcon = it.color,
                iconId = it.iconId,
            )
        }
        assertThat(viewModel.uiState.value.categories).containsAnyIn(expectedCategoriesList)
    }


    @Test
    fun setDescription_addDescriptionNewEntry() {
        val expectedDescription = "fake Description"
        viewModel.setDescription(expectedDescription)

        assertThat(viewModel.uiState.value.about).contains(expectedDescription)
    }


    @Test
    fun setCost_addSumNewEntry() {
        val amount = "30"

        viewModel.setCost(amount)

        assertThat(viewModel.uiState.value.amount).contains(amount)
        println(viewModel.uiState.value.amount)
    }

    @Test
    fun setAmount_giveString_parseAndCalculateCost() {
        val amount = "2 * 40 ="
        viewModel.setCost(amount)

        assertThat(viewModel.uiState.value.amount).contains("80")
    }


    @Test
    fun setDate_selectedDateIs0() {
        val selectedDate = 0L
        viewModel.setDate(selectedDate)
        assertThat(viewModel.uiState.value.selectedDate).isEqualTo(selectedDate)
    }


    @Test
    fun setCategory_addIdNewEntry() {
        val selectedCategoryId = 0L
        viewModel.setCategory(selectedCategoryId)

        assertThat(viewModel.uiState.value.selectedCategoryId).isEqualTo(selectedCategoryId)

    }


    @Test
    fun deleteCategoryById_CategoryNotActive() = runTest {
        repository.setCategory(fakeDeletedCategory)
        viewModel.deleteCategoryById(fakeDeletedCategory.id)
        viewModel.updateData()

        val expectedListIdCategories: List<Long> = viewModel.uiState.value.categories
            .map { it.id }
        assertThat(expectedListIdCategories).doesNotContain(fakeDeletedCategory.id)
    }

    @Test
    fun deleteCategoryById_CategoryIsActive() = runTest {
        val fakeDeletedCategoryId = 3L

        viewModel.setCategory(fakeDeletedCategoryId)
        viewModel.updateData()

        assertThat(viewModel.uiState.value.selectedCategoryId).isNotEqualTo(fakeDeletedCategory)
    }


    @Test
    fun setIsIncomeValue_givenTrue_setListIncomeCategory() {
        val changeValue = true
        viewModel.setIsIncomeValue(changeValue)

        assertThat(viewModel.uiState.value.isIncome).isTrue()

    }

    @Test
    fun setIsIncomeValue_givenFalse_setListExpenditureCategory() {
        val changeValue = false
        viewModel.setIsIncomeValue(changeValue)

        assertThat(viewModel.uiState.value.isIncome).isFalse()
    }


    @Test
    fun addCostToDb_categoryIdIsNull_errorCategoryNotSelected() {
        viewModel.addCostToDb(isPlanned = false)

        assertThat(viewModel.uiState.value.addCostMessageId.toString())
            .contains(AddCostMessage.CATEGORY_NOT_SELECTED.stringResource.toString())
        assertThat(viewModel.uiState.value.isLoading).isFalse()
    }

    @Test
    fun addCostToDb_amountIsEmpty_errorAmountIsEmpty() {
        viewModel.setCategory(0L)
        viewModel.addCostToDb(isPlanned = false)

        assertThat(viewModel.uiState.value.addCostMessageId.toString())
            .contains(AddCostMessage.AMOUNT_IS_EMPTY.stringResource.toString())
        assertThat(viewModel.uiState.value.isError).isTrue()
        assertThat(viewModel.uiState.value.isLoading).isFalse()
    }

    @Test
    fun addCostToDb_amountIsNotOnlyDigit_errorAmountNotNumber() {

        viewModel.setCategory(0L)
        viewModel.setCost("2+2=")
        viewModel.addCostToDb(isPlanned = false)

        assertThat(viewModel.uiState.value.addCostMessageId.toString())
            .contains(AddCostMessage.AMOUNT_NOT_INT.stringResource.toString())
        assertThat(viewModel.uiState.value.isError).isTrue()
        assertThat(viewModel.uiState.value.isLoading).isFalse()
    }

    @Test
    fun addCostToDb_amountOverLimitOrOther_errorNumberFormatException() {
        viewModel.setCategory(0L)
        viewModel.setCost("2147483648")
        viewModel.addCostToDb(isPlanned = false)

        assertThat(viewModel.uiState.value.addCostMessageId.toString())
            .contains(AddCostMessage.AMOUNT_OVER_LIMIT.stringResource.toString())
        assertThat(viewModel.uiState.value.isError).isTrue()
        assertThat(viewModel.uiState.value.isLoading).isFalse()
    }

    @Test
    fun addCostToDb_errorsNotFond_createNewEntry() {
        val expectedCost = Cost(
            id = 0L,
            categoryId = 0L,
            amount = 2147483647,
            date = 0L,
            about = "fake about",
        )

        viewModel.setCategory(0L)
        viewModel.setCost("2147483647")
        viewModel.setDate(0L)
        viewModel.setDescription("fake about")
        viewModel.addCostToDb(isPlanned = false)

        assertThat(repository.fakeCost).contains(expectedCost)
    }


    @Test
    fun amountIsDigit_returnFalse() {
        viewModel.setCost("2+2=")
        assertThat(viewModel.uiState.value.amountIsDigit()).isFalse()
    }

    @Test
    fun amountIsDigit_returnTrue() {
        viewModel.setCost("4")
        assertThat(viewModel.uiState.value.amountIsDigit()).isTrue()
    }
}
