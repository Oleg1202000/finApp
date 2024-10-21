package com.mk1morebugs.finapp.data

import com.mk1morebugs.finapp.data.local.room.Category
import com.mk1morebugs.finapp.data.local.room.CategoryWithoutIsIncome
import com.mk1morebugs.finapp.data.local.room.Planned
import com.mk1morebugs.finapp.data.local.room.ReturnPlanAmount
import com.mk1morebugs.finapp.data.local.room.ReturnPlannedHistory
import com.mk1morebugs.finapp.data.local.room.ReturnSumAmount
import com.mk1morebugs.finapp.data.local.room.ReturnSummaryHistory
import com.mk1morebugs.finapp.data.local.room.Summary
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