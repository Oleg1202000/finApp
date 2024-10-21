package com.mk1morebugs.finapp.data

import com.mk1morebugs.finapp.data.local.room.Category
import com.mk1morebugs.finapp.data.local.room.Planned
import com.mk1morebugs.finapp.data.local.room.Summary
import com.mk1morebugs.finapp.data.local.room.dao.CategoriesDao
import com.mk1morebugs.finapp.data.local.room.dao.PlanDao
import com.mk1morebugs.finapp.data.local.room.dao.SummaryDao
import com.mk1morebugs.finapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val categoriesDao: CategoriesDao,
    private val summaryDao: SummaryDao,
    private val planDao: PlanDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : Repository {


    // Categories table
    override fun getCategories (isIncome: Boolean) = categoriesDao.getCategories(isIncome)
        .flowOn(dispatcher)

    override suspend fun setCategory (category: Category) = categoriesDao.setCategory(category)

    override suspend fun deleteCategoryById (id: Long) = categoriesDao.deleteCategoryById(id)


    // Planned table
    override fun getPlan(
        isIncome: Boolean,
        beginDate: Long,
        endDate: Long
    ) = planDao.getPlan(isIncome, beginDate, endDate).flowOn(dispatcher)

    override suspend fun setPlan (plan: Planned) = planDao.setPlan(plan)

    override suspend fun deletePlanById (id: Long) = planDao.deletePlanById(id)

    override fun getPlannedHistory(
        beginDate: Long,
        endDate: Long
    ) = planDao.getPlannedHistory(endDate, beginDate).flowOn(dispatcher)


    // Summary table
    override fun getSumAmount(
        isIncome: Boolean,
        beginDate: Long,
        endDate: Long
    ) = summaryDao.getSumAmount(isIncome, beginDate, endDate).flowOn(dispatcher)

    override fun getSummaryHistory(
        beginDate: Long,
        endDate: Long
    ) = summaryDao.getSummaryHistory(endDate, beginDate).flowOn(dispatcher)

    override suspend fun setSummary(summary: Summary) = summaryDao.setSummary(summary)

    override suspend fun deleteSummaryById(id: Long) = summaryDao.deleteSummaryById(id)

    override suspend fun updateSummary(summary: Summary) = summaryDao.updateSummary(summary)
}