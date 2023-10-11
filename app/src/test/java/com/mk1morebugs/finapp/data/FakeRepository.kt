package com.mk1morebugs.finapp.data

import com.mk1morebugs.finapp.data.database.Category
import com.mk1morebugs.finapp.data.database.CategoryWithoutIsIncome
import com.mk1morebugs.finapp.data.database.Planned
import com.mk1morebugs.finapp.data.database.ReturnPlanAmount
import com.mk1morebugs.finapp.data.database.ReturnPlannedHistory
import com.mk1morebugs.finapp.data.database.ReturnSumAmount
import com.mk1morebugs.finapp.data.database.ReturnSummaryHistory
import com.mk1morebugs.finapp.data.database.Summary
import com.mk1morebugs.finapp.di.IRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository : IRepository {

    private var fakeCategories: MutableList<Category> = mutableListOf()
    var fakeSummary: MutableList<Summary> = mutableListOf()


    override fun getCategories(isIncome: Boolean): Flow<List<CategoryWithoutIsIncome>> = flow {
        emit(
            fakeCategories.map {
                CategoryWithoutIsIncome(
                    id = it.id,
                    name = it.name,
                    color = it.color,
                    iconId = it.iconId,
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

    override fun getPlan(
        isIncome: Boolean,
        beginDate: Long,
        endDate: Long
    ): Flow<List<ReturnPlanAmount>> {
        TODO("Not yet implemented")
    }

    override suspend fun setPlan(plan: Planned) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePlanById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun getPlannedHistory(
        beginDate: Long,
        endDate: Long
    ): Flow<List<ReturnPlannedHistory>> {
        TODO("Not yet implemented")
    }

    override fun getSumAmount(
        isIncome: Boolean,
        beginDate: Long,
        endDate: Long
    ): Flow<List<ReturnSumAmount>> {
        TODO("Not yet implemented")
    }

    override fun getSummaryHistory(
        beginDate: Long,
        endDate: Long
    ): Flow<List<ReturnSummaryHistory>> {
        TODO("Not yet implemented")
    }

    override suspend fun setSummary(summary: Summary) {
        fakeSummary.add(summary)
    }

    override suspend fun deleteSummaryById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun updateSummary(summary: Summary) {
        TODO("Not yet implemented")
    }

}