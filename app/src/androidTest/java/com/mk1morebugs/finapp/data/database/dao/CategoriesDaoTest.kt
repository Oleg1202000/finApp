package com.mk1morebugs.finapp.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.mk1morebugs.finapp.data.local.room.Category
import com.mk1morebugs.finapp.data.local.room.CategoryWithoutIsIncome
import com.mk1morebugs.finapp.data.local.room.FinappDatabase
import com.mk1morebugs.finapp.data.local.room.dao.CategoriesDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@SmallTest
class CategoriesDaoTest {

    private lateinit var database: FinappDatabase
    private lateinit var categoriesDao: CategoriesDao


    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FinappDatabase::class.java
        ).build()

        categoriesDao = database.categoriesDao()
    }

    @After
    fun closeDb() = database.close()


    @Test
    fun setCategory_insertCategoryWithoutId() = runTest {

        categoriesDao.setCategory(insertedCategory)

        val loaded: List<CategoryWithoutIsIncome> = categoriesDao.getCategories()
            .firstOrNull() ?: listOf()

        assertThat(loaded.size).isNotEqualTo(0)
        assertThat(loaded[0].name).isEqualTo(insertedCategory.name)
        assertThat(loaded[0].color).isEqualTo(insertedCategory.color)
        assertThat(loaded[0].iconId).isEqualTo(insertedCategory.iconId)
    }


    @Test
    fun getCategories_returnEmptyList() = runTest {

        val loaded: List<CategoryWithoutIsIncome> = categoriesDao.getCategories()
            .firstOrNull() ?: listOf()

        assertThat(loaded.size).isEqualTo(0)
    }

    @Test
    fun getCategories_returnListCategoriesWithoutIsIncome() = runTest {

        val expectedValue = fakeCategories.map {
            CategoryWithoutIsIncome(
                id = it.id,
                name = it.name,
                color = it.color,
                iconId = it.iconId,
            )
        }

        fakeCategories.forEach {
            categoriesDao.setCategory(
                category = Category(
                    name = it.name,
                    isIncome = it.isIncome,
                    color = it.color,
                    iconId = it.iconId,
                )
            )
        }

        val loaded: List<CategoryWithoutIsIncome> = categoriesDao.getCategories()
            .firstOrNull() ?: listOf()

        assertThat(loaded).containsAnyIn(expectedValue)
    }

    @Test
    fun deleteCategoryById() = runTest {
        fakeCategories.forEach {
            categoriesDao.setCategory(
                category = Category(
                    name = it.name,
                    isIncome = it.isIncome,
                    color = it.color,
                    iconId = it.iconId,
                )
            )
        }

        categoriesDao.deleteCategoryById(fakeCategories[1].id)

        val loaded = categoriesDao.getCategories().firstOrNull() ?: listOf()

        assertThat(loaded).doesNotContain(fakeCategories[1])
    }
}