package com.mk1morebugs.finapp.data

import com.mk1morebugs.finapp.data.local.room.Category
import com.mk1morebugs.finapp.data.local.room.CategoryWithoutIsIncome
import com.mk1morebugs.finapp.data.local.room.Cost
import com.mk1morebugs.finapp.data.local.room.CostForUi
import com.mk1morebugs.finapp.data.local.room.CostHistory
import kotlinx.coroutines.flow.Flow


interface Repository {
    fun getCategories(isIncome: Boolean): Flow<List<CategoryWithoutIsIncome>>

    suspend fun setCategory(category: Category)

    suspend fun deleteCategoryById(id: Long)

    fun getCosts(
        isIncome: Boolean = false,
        isPlanned: Boolean,
        beginDate: Long,
        endDate: Long,
    ): Flow<List<CostForUi>>

    fun getCostsHistory(
        beginDate: Long,
        endDate: Long,
        isPlanned: Boolean,
    ): Flow<List<CostHistory>>

    suspend fun setCost(cost: Cost)

    suspend fun deleteCostById(id: Long)

    suspend fun updateCost(cost: Cost)
}