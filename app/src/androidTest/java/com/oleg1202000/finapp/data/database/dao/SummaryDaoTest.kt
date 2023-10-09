package com.oleg1202000.finapp.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.oleg1202000.finapp.data.database.FinappDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@SmallTest
class SummaryDaoTest {

    private lateinit var database: FinappDatabase

    private lateinit var planDao: PlanDao
    private lateinit var categoriesDao: CategoriesDao
    private lateinit var summaryDao: SummaryDao


    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FinappDatabase::class.java
        ).build()

        planDao = database.planDao()
        categoriesDao = database.categoriesDao()
        summaryDao = database.summaryDao()
    }

    @After
    fun closeDb() = database.close()


    @Test
    fun getSumAmount_returnSumAmount() = runTest {
        fakeCategories.forEach {
            categoriesDao.setCategory(it)
        }
        fakePlans.forEach {
            planDao.setPlan(it)
        }
        fakeSummaries.forEach {
            summaryDao.setSummary(it)
        }

        val loadedData = summaryDao.getSumAmount(beginDate = 0L, endDate = 1L)
            .firstOrNull()

        assertThat(loadedData).isEqualTo(factSummaryAmount)

    }

    @Test
    fun getSummaryHistory_returnSummaryHistory() = runTest {

        fakeCategories.forEach {
            categoriesDao.setCategory(it)
        }
        fakeSummaries.forEach {
            summaryDao.setSummary(it)
        }

        val loadedData = summaryDao.getSummaryHistory(
            beginDate = 0L, endDate = 1L
        )
            .firstOrNull()

        assertThat(loadedData).isEqualTo(factSummaryHistory)
    }

    @Test
    fun setSummary_insertSummary() = runTest {

        categoriesDao.setCategory(fakeCategories[0])
        summaryDao.setSummary(fakeSummaries[0])

        val loadedData = summaryDao.getSummaryHistory(beginDate = 0L, endDate = 1L)
            .firstOrNull() ?: emptyList()

        assertThat(loadedData.size).isNotEqualTo(0)
        assertThat(loadedData[0]).isEqualTo(factSummaryHistory[0])
    }

    @Test
    fun deleteSummaryById() = runTest {

        fakeCategories.forEach {
            categoriesDao.setCategory(it)
        }
        fakeSummaries.forEach {
            summaryDao.setSummary(it)
        }

        summaryDao.deleteSummaryById(id = 1L)

        val loadedData = summaryDao.getSummaryHistory(beginDate = 0L, endDate = 1L)
            .firstOrNull()

        assertThat(loadedData).doesNotContain(fakeSummaries[0])
    }

    @Test
    fun updateSummary() = runTest {

        fakeCategories.forEach {
            categoriesDao.setCategory(it)
        }
        fakeSummaries.forEach {
            summaryDao.setSummary(it)
        }

        summaryDao.updateSummary(newSummaryItem)

        val loadedData = summaryDao.getSummaryHistory(beginDate = 0L, endDate = 1L)
            .firstOrNull() ?: emptyList()

        assertThat(loadedData[0].about).isEqualTo(newSummaryItem.about)
    }
}