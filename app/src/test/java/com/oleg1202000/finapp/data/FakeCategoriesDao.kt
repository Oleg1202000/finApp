package com.oleg1202000.finapp.data

import com.oleg1202000.finapp.data.database.Category
import com.oleg1202000.finapp.data.database.CategoryWithoutIsIncome
import com.oleg1202000.finapp.data.database.dao.CategoriesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCategoriesDao : CategoriesDao {

    override fun getCategories(
        isIncome: Boolean
    ) : Flow<List<CategoryWithoutIsIncome>> = flow {
        emit(fakeCategories.filter { it.isIncome == isIncome }.map {

            CategoryWithoutIsIncome(
                id = it.id,
                name = it.name,
                color = it.color,
                iconId = it.iconId
            )
        }
        )
    }

    override suspend fun setCategory(category: Category) {
        fakeCategories.add(category)
    }

    override suspend fun deleteCategoryById(id: Long) {
        fakeCategories.remove(
            fakeCategories.find {
                it.id == id
            }
        )
    }
}