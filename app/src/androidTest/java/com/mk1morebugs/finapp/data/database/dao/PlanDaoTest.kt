package com.mk1morebugs.finapp.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.mk1morebugs.finapp.data.database.FinappDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.firstOrNull
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@SmallTest
class PlanDaoTest {

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
    fun getPlan_returnPlannedAmount() = runTest {

        fakeCategories.forEach {
            categoriesDao.setCategory(it)
        }
        fakeSummaries.forEach {
            summaryDao.setSummary(it)
        }
        fakePlans.forEach {
            planDao.setPlan(it)
        }

        val loadedData = planDao.getPlan(beginDate = 0L, endDate = 1L)
            .firstOrNull()

        assertThat(loadedData).isEqualTo(plannedValue)
    }

    @Test
    fun getPlannedHistory_returnPlannedHistory() = runTest {

        fakeCategories.forEach {
            categoriesDao.setCategory(it)
        }
        fakePlans.forEach {
            planDao.setPlan(it)
        }

        val loadedData = planDao.getPlannedHistory(beginDate = 0L, endDate = 1L)
            .firstOrNull()

        assertThat(loadedData).isEqualTo(plannedHistory)
    }

    @Test
    fun setPlan_insertPlan() = runTest {

        categoriesDao.setCategory(fakeCategories[0])
        planDao.setPlan(fakePlans[0])

        val loadedData = planDao.getPlannedHistory(beginDate = 0L, endDate = 1L)
            .firstOrNull() ?: emptyList()

        assertThat(loadedData.size).isNotEqualTo(0)
        assertThat(loadedData[0]).isEqualTo(plannedHistory[0])
    }

    @Test
    fun deletePlanById() = runTest {

        fakeCategories.forEach {
            categoriesDao.setCategory(it)
        }
        fakePlans.forEach {
            planDao.setPlan(it)
        }

        planDao.deletePlanById(id = 1L)

        val loadedData = planDao.getPlannedHistory(beginDate = 0L, endDate = 1L)
            .firstOrNull()

        assertThat(loadedData).doesNotContain(fakePlans[0])
    }
}