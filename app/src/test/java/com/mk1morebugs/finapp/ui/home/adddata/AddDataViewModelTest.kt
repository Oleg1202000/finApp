package com.mk1morebugs.finapp.ui.home.adddata

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
class AddDataViewModelTest {
    private lateinit var repository: FakeRepository
    private lateinit var viewModel: AddDataViewModel

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

        viewModel = AddDataViewModel(localRepository = repository)
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
    fun setAmount_addSumNewEntry() {
        val amount = "30"

        viewModel.setAmount(amount)

        assertThat(viewModel.uiState.value.amount).contains(amount)
        println(viewModel.uiState.value.amount)
    }

    @Test
    fun setAmount_giveString_parseAndCalculateAmount() {
        val amount = "2 * 40 ="
        viewModel.setAmount(amount)

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
    fun addData_categoryIdIsNull_errorCategoryNotSelected() {
        viewModel.addData()

        assertThat(viewModel.uiState.value.errorMessage.toString())
            .contains(ErrorMessage.CategoryNotSelected.toString())
        assertThat(viewModel.uiState.value.isLoading).isFalse()
    }

    @Test
    fun addData_amountIsEmpty_errorAmountIsEmpty() {
        viewModel.setCategory(0L)
        viewModel.addData()

        assertThat(viewModel.uiState.value.errorMessage.toString())
            .contains(ErrorMessage.AmountIsEmpty.toString())
        assertThat(viewModel.uiState.value.errorTextField).isTrue()
        assertThat(viewModel.uiState.value.isLoading).isFalse()
    }

    @Test
    fun addData_amountIsNotOnlyDigit_errorAmountNotNumber() {

        viewModel.setCategory(0L)
        viewModel.setAmount("2+2=")
        viewModel.addData()

        assertThat(viewModel.uiState.value.errorMessage.toString())
            .contains(ErrorMessage.AmountNotInt.toString())
        assertThat(viewModel.uiState.value.errorTextField).isTrue()
        assertThat(viewModel.uiState.value.isLoading).isFalse()
    }

    @Test
    fun addData_amountOverLimitOrOther_errorNumberFormatException() {
        viewModel.setCategory(0L)
        viewModel.setAmount("2147483648")
        viewModel.addData()

        assertThat(viewModel.uiState.value.errorMessage.toString())
            .contains(ErrorMessage.AmountOverLimit.toString())
        assertThat(viewModel.uiState.value.errorTextField).isTrue()
        assertThat(viewModel.uiState.value.isLoading).isFalse()
    }

    @Test
    fun addData_errorsNotFond_createNewEntry() {
        val expectedCost = Cost(
            id = 0L,
            categoryId = 0L,
            amount = 2147483647,
            date = 0L,
            about = "fake about",
        )

        viewModel.setCategory(0L)
        viewModel.setAmount("2147483647")
        viewModel.setDate(0L)
        viewModel.setDescription("fake about")
        viewModel.addData()

        assertThat(repository.fakeCost).contains(expectedCost)
    }


    @Test
    fun amountIsDigit_returnFalse() {
        viewModel.setAmount("2+2=")
        assertThat(viewModel.uiState.value.amountIsDigit()).isFalse()
    }

    @Test
    fun amountIsDigit_returnTrue() {
        viewModel.setAmount("4")
        assertThat(viewModel.uiState.value.amountIsDigit()).isTrue()
    }
}
