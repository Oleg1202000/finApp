package com.oleg1202000.finapp.di

import com.oleg1202000.finapp.data.Category
import com.oleg1202000.finapp.data.CategoryWithoutIsIncome
import com.oleg1202000.finapp.data.Planned
import com.oleg1202000.finapp.data.ReturnPlanAmount
import com.oleg1202000.finapp.data.ReturnPlannedHistory
import com.oleg1202000.finapp.data.ReturnSumAmount
import com.oleg1202000.finapp.data.ReturnSummaryHistory
import com.oleg1202000.finapp.data.Summary
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