package com.oleg1202000.finapp.di

import com.oleg1202000.finapp.data.database.Category
import com.oleg1202000.finapp.data.database.Planned
import com.oleg1202000.finapp.data.database.Summary
import com.oleg1202000.finapp.data.database.dao.CategoriesDao
import com.oleg1202000.finapp.data.database.dao.PlanDao
import com.oleg1202000.finapp.data.database.dao.SummaryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class Repository @Inject constructor(

    private val categoriesDao: CategoriesDao,
    private val summaryDao: SummaryDao,
    private val planDao: PlanDao,
) : IRepository {


    // Categories table
    override fun getCategories (isIncome: Boolean) =
        categoriesDao.getCategories(isIncome).flowOn(Dispatchers.IO)

    override suspend fun setCategory (category: Category) = categoriesDao.setCategory(category)

    override suspend fun deleteCategoryById (id: Long) = categoriesDao.deleteCategoryById(id)


    // Planned table
    override fun getPlan(
        isIncome: Boolean,
        beginDate: Long,
        endDate: Long
    ) = planDao.getPlan(isIncome, beginDate, endDate).flowOn(Dispatchers.IO)

    override suspend fun setPlan (plan: Planned) = planDao.setPlan(plan)

    override suspend fun deletePlanById (id: Long) = planDao.deletePlanById(id)

    override fun getPlannedHistory(
        beginDate: Long,
        endDate: Long
    ) = planDao.getHistory(endDate, beginDate).flowOn(Dispatchers.IO)


    // Summary table
    override fun getSumAmount(
        isIncome: Boolean,
        beginDate: Long,
        endDate: Long
    ) = summaryDao.getSumAmount(isIncome, beginDate, endDate).flowOn(Dispatchers.IO)

    override fun getSummaryHistory(
        beginDate: Long,
        endDate: Long
    ) = summaryDao.getHistory(endDate, beginDate).flowOn(Dispatchers.IO)

    override suspend fun setSummary(summary: Summary) = summaryDao.setSummary(summary)

    override suspend fun deleteSummaryById(id: Long) = summaryDao.deleteSummaryById(id)

    override suspend fun updateSummary(summary: Summary) = summaryDao.updateSummary(summary)
}