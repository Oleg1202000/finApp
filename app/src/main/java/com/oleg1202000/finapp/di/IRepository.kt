package com.oleg1202000.finapp.di

import com.oleg1202000.finapp.data.database.Category
import com.oleg1202000.finapp.data.database.CategoryWithoutIsIncome
import com.oleg1202000.finapp.data.database.Planned
import com.oleg1202000.finapp.data.database.ReturnPlanAmount
import com.oleg1202000.finapp.data.database.ReturnPlannedHistory
import com.oleg1202000.finapp.data.database.ReturnSumAmount
import com.oleg1202000.finapp.data.database.ReturnSummaryHistory
import com.oleg1202000.finapp.data.database.Summary
import kotlinx.coroutines.flow.Flow


interface IRepository {
    // Categories table
   fun getCategories (isIncome: Boolean): Flow<List<CategoryWithoutIsIncome>>

    suspend fun setCategory (category: Category)

    suspend fun deleteCategoryById (id: Long)

    // Planned table
fun getPlan(
        isIncome: Boolean,
        beginDate: Long,
        endDate: Long
 ): Flow<List<ReturnPlanAmount>>

    suspend fun setPlan (plan: Planned)

    suspend fun deletePlanById (id: Long)
    fun getPlannedHistory(
        beginDate: Long,
        endDate: Long
): Flow<List<ReturnPlannedHistory>>

    // Summary table
fun getSumAmount(
        isIncome: Boolean = false,
        beginDate: Long,
        endDate: Long
): Flow<List<ReturnSumAmount>>

    fun getSummaryHistory(
        beginDate: Long,
        endDate: Long
): Flow<List<ReturnSummaryHistory>>

    suspend fun setSummary(summary: Summary)

    suspend fun deleteSummaryById(id: Long)

    suspend fun updateSummary(summary: Summary)
}