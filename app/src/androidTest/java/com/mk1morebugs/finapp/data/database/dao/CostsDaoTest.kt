package com.mk1morebugs.finapp.data.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.mk1morebugs.finapp.data.local.room.Cost
import com.mk1morebugs.finapp.data.local.room.FinappDatabase
import com.mk1morebugs.finapp.data.local.room.dao.CategoriesDao
import com.mk1morebugs.finapp.data.local.room.dao.CostsDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@SmallTest
class CostsDaoTest {

    private lateinit var database: FinappDatabase

    private lateinit var categoriesDao: CategoriesDao
    private lateinit var costsDao: CostsDao

    @Before
    fun setUp() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            FinappDatabase::class.java
        ).build()

        categoriesDao = database.categoriesDao()
        costsDao = database.costsDao()

        fakeCategories.forEach {
            categoriesDao.setCategory(it)
        }

        fakeCosts.forEach {
            costsDao.setCost(it)
        }
    }

    @After
    fun closeDb() = database.close()


    @Test
    fun getCostForUi_readFactCostGroupByCategoryForSelectedPeriod_returnFactCostsForUi() = runTest {
        val costsGroupByCategory = costsDao.getCostsForUi(
            beginDate = 1L,
            endDate = 4L,
            isIncome = false,
            isPlanned = false,
            ).first()

        assertThat(costsGroupByCategory[0].summaryAmount).isEqualTo(400)
        assertThat(costsGroupByCategory[0].categoryName).isEqualTo("test name 2")
        assertThat(costsGroupByCategory[0].iconId).isEqualTo(0)
        assertThat(costsGroupByCategory[0].iconColor).isEqualTo(0xFFD32F2F)

        assertThat(costsGroupByCategory[1].summaryAmount).isEqualTo(111 + 200)
        assertThat(costsGroupByCategory[1].categoryName).isEqualTo("test name 1")
        assertThat(costsGroupByCategory[1].iconId).isEqualTo(0)
        assertThat(costsGroupByCategory[1].iconColor).isEqualTo(0xFFD32F2F)
    }

    @Test
    fun getCostForUi_readPlannedCostGroupByCategoryForSelectedPeriod_returnPlannedCostsForUi() = runTest {
        val costsGroupByCategory = costsDao.getCostsForUi(
            beginDate = 1L,
            endDate = 4L,
            isIncome = false,
            isPlanned = true,
        ).first()

        assertThat(costsGroupByCategory[0].summaryAmount).isEqualTo(200)
        assertThat(costsGroupByCategory[0].categoryName).isEqualTo("test name 1")
        assertThat(costsGroupByCategory[0].iconId).isEqualTo(0)
        assertThat(costsGroupByCategory[0].iconColor).isEqualTo(0xFFD32F2F)
    }

    @Test
    fun getCostsForUi_readFactIncomeGroupByCategoryForSelectedPeriod_returnFactIncomeForUi() = runTest {
        val costsGroupByCategory = costsDao.getCostsForUi(
            beginDate = 1L,
            endDate = 4L,
            isIncome = true,
            isPlanned = false,
        ).first()

        assertThat(costsGroupByCategory[0].summaryAmount).isEqualTo(40000)
        assertThat(costsGroupByCategory[0].categoryName).isEqualTo("test name 3")
        assertThat(costsGroupByCategory[0].iconId).isEqualTo(0)
        assertThat(costsGroupByCategory[0].iconColor).isEqualTo(0xFFD32F2F)
    }

    @Test
    fun getCostHistory_getHistoryOfAddingFactCostAndIncomeInReverseOrder_returnCostsHistory() = runTest {

        val costsWithoutGrouping = costsDao.getCostsHistory(
            beginDate = 1L,
            endDate = 4L,
            isPlanned = false
        ).first()

        assertThat(costsWithoutGrouping[0].id).isEqualTo(7L)
        assertThat(costsWithoutGrouping[0].categoryName).isEqualTo("test name 2")
        assertThat(costsWithoutGrouping[0].isIncome).isEqualTo(false)
        assertThat(costsWithoutGrouping[0].iconId).isEqualTo(0)
        assertThat(costsWithoutGrouping[0].iconColor).isEqualTo(0xFFD32F2F)
        assertThat(costsWithoutGrouping[0].amount).isEqualTo(400)
        assertThat(costsWithoutGrouping[0].date).isEqualTo(4L)
        assertThat(costsWithoutGrouping[0].about).isEqualTo(null)

        assertThat(costsWithoutGrouping.size).isEqualTo(4)
    }

    @Test
    fun deleteCostById() = runTest {

        costsDao.deleteCostById(id = 5L)
        costsDao.deleteCostById(id = 6L)

        val historyCosts = costsDao.getCostsHistory(
            beginDate = 0L,
            endDate = 5L,
            isPlanned = false
        ).first()

        assertThat(historyCosts).doesNotContain(fakeCosts[4])
        assertThat(historyCosts).doesNotContain(fakeCosts[5])
        assertThat(historyCosts.size).isEqualTo(4)
    }

    @Test
    fun updateCost() = runTest {
        costsDao.updateCost(
            cost = Cost(
                id = 7L,
                categoryId = 1L,
                amount = 100,
                date = 4L,
                isPlanned = false,
                about = "update item",
            )
        )

        val historyCosts = costsDao.getCostsHistory(
            beginDate = 1L,
            endDate = 4L,
            isPlanned = false
        ).first()

        assertThat(historyCosts[0].about).isEqualTo("update item")
        assertThat(historyCosts[0].categoryName).isEqualTo("test name 1")
        assertThat(historyCosts[0].amount).isEqualTo(100)
    }
}