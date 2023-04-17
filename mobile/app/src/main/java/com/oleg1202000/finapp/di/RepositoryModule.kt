package com.oleg1202000.finapp.di

import com.oleg1202000.finapp.data.*
import com.oleg1202000.finapp.data.dao.CategoriesDao
import com.oleg1202000.finapp.data.dao.PlanDao
import com.oleg1202000.finapp.data.dao.SummaryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.sql.Date
import javax.inject.Inject


 class RepositoryModule @Inject constructor(

    private val categoriesDao: CategoriesDao,
    private val summaryDao: SummaryDao,
    private val planDao: PlanDao
 ) {


     // Categories table
    fun getCategries(isIncome: Boolean) = flow {
        emit(categoriesDao.getCategories(isIncome))
    }.flowOn(Dispatchers.IO)

    fun setCategory (category: Categories) = categoriesDao.setCategory(category)

    fun deleteCategory (category: Categories) = categoriesDao.deleteCategory(category)


     // Planned table
    fun getPlan(isIncome: Boolean, beginDate: Date?, endDate: Date?) = flow {
        emit(planDao.getPlan(isIncome, beginDate, endDate))
    }.flowOn(Dispatchers.IO)

    fun setPlan (plan: Planned) = planDao.setPlan(plan)

     fun deletePlan (plan: Planned) = planDao.deletePlan(plan)


     // Summary table
    fun getSumAmount(
        isIncome: Boolean = false,
        beginDate: Date?,
        endDate: Date?
    ) = flow {
        emit(summaryDao.getSumAmount(isIncome, beginDate, endDate))
    }.flowOn(Dispatchers.IO)

    fun getHistory(
        endDate: Date?,
        beginDate: Date?
    ) = flow {
        emit(summaryDao.getHistory(endDate, beginDate))
    }.flowOn(Dispatchers.IO)

    fun setSummary(summary: Summary) = summaryDao.setSummary(summary)

    fun deleteSummary(id: Long) = summaryDao.deleteSummaryById(id)

    fun updateSummary(summary: Summary) = summaryDao.updateSummary(summary)
}