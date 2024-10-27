package com.mk1morebugs.finapp.ui.history

import com.mk1morebugs.finapp.data.FakeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModelTest {
    private lateinit var repository: FakeRepository
    private lateinit var viewModel: HistoryViewModel
    private lateinit var uiState: StateFlow<HistoryUiState>

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() = runTest {
        Dispatchers.setMain(testDispatcher)

        repository = FakeRepository()
        viewModel = HistoryViewModel(repository = repository)
        uiState = viewModel.uiState
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun getUiState_withEmptyListOfCostHistory_returnEmptyList() {
        viewModel.loadCostHistory()
        assertThat(uiState.value.historyGroupByDate).isEmpty()
    }

    @Test
    fun getUiState_withNonEmptyListOfCostHistory_returnListHistoryGroupByDate() = runTest(testDispatcher) {
        repository.initFakeCostsHistory(listOfCostHistory = listOfFakeCosts)

        viewModel.loadCostHistory()

        println(uiState.value.historyGroupByDate)
        assertThat(uiState.value.historyGroupByDate.size).isEqualTo(3)
        assertThat(uiState.value.historyGroupByDate[0].historyItems.size).isEqualTo(2)
        assertThat(uiState.value.historyGroupByDate[1].historyItems.size).isEqualTo(2)
        assertThat(uiState.value.historyGroupByDate[2].historyItems.size).isEqualTo(3)

        assertThat(uiState.value.historyGroupByDate[2].historyItems[2].id).isEqualTo(listOfFakeCosts[6].id)
        assertThat(uiState.value.historyGroupByDate[2].historyItems[2].categoryName).isEqualTo(listOfFakeCosts[6].categoryName)
        assertThat(uiState.value.historyGroupByDate[2].historyItems[2].iconId).isEqualTo(listOfFakeCosts[6].iconId)
        assertThat(uiState.value.historyGroupByDate[2].historyItems[2].iconColor).isEqualTo(listOfFakeCosts[6].iconColor)
        assertThat(uiState.value.historyGroupByDate[2].historyItems[2].amount).isEqualTo(listOfFakeCosts[6].amount)
        assertThat(uiState.value.historyGroupByDate[2].historyItems[2].about).isEqualTo(listOfFakeCosts[6].about)
    }
}