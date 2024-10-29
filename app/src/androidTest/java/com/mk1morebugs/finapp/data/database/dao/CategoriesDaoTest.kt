package com.mk1morebugs.finapp.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.mk1morebugs.finapp.data.local.room.CategoryWithoutIsIncome
import com.mk1morebugs.finapp.data.local.room.FinappDatabase
import com.mk1morebugs.finapp.data.local.room.dao.CategoriesDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@SmallTest
class CategoriesDaoTest {

    private lateinit var database: FinappDatabase
    private lateinit var categoriesDao: CategoriesDao

    @Before
    fun setUp() = runTest {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FinappDatabase::class.java
        ).build()

        categoriesDao = database.categoriesDao()

        fakeCategories.forEach {
            categoriesDao.setCategory(it)
        }
    }

    @After
    fun closeDb() = database.close()


    @Test
    fun getCategories_insertCostCategory_returnListCategoriesWithoutIsIncomeField() = runTest {
        val listCostCategories: List<CategoryWithoutIsIncome> = categoriesDao.getCategories().first()

        assertThat(listCostCategories[0].name).isEqualTo(fakeCategories[0].name)
        assertThat(listCostCategories[0].iconColor).isEqualTo(fakeCategories[0].color)
        assertThat(listCostCategories[0].iconId).isEqualTo(fakeCategories[0].iconId)
        assertThat(listCostCategories.size).isEqualTo(2)
    }

    @Test
    fun getCategories_insertIncomeCategory_returnListCategoriesWithoutIsIncomeField() = runTest {
        val listIncomeCategories: List<CategoryWithoutIsIncome> = categoriesDao.getCategories(
            isIncome = true
        ).first()

        assertThat(listIncomeCategories[0].name).isEqualTo(fakeCategories[2].name)
        assertThat(listIncomeCategories[0].iconColor).isEqualTo(fakeCategories[2].color)
        assertThat(listIncomeCategories[0].iconId).isEqualTo(fakeCategories[2].iconId)
        assertThat(listIncomeCategories.size).isEqualTo(1)
    }

    @Test
    fun deleteCategoryById() = runTest {
        categoriesDao.deleteCategoryById(fakeCategories[1].id)

        val listCostCategories = categoriesDao.getCategories().first()
        val listIncomeCategories: List<CategoryWithoutIsIncome> = categoriesDao.getCategories(
            isIncome = true
        ).first()

        assertThat(listCostCategories).doesNotContain(fakeCategories[1])
        assertThat(listCostCategories.size).isEqualTo(1)
        assertThat(listIncomeCategories).doesNotContain(fakeCategories[1])
        assertThat(listCostCategories.size).isEqualTo(1)
    }
}