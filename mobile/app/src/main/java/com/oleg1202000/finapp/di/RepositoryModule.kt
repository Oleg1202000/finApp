package com.oleg1202000.finapp.di

import com.oleg1202000.finapp.data.*
import com.oleg1202000.finapp.data.dao.CategoriesDao
import com.oleg1202000.finapp.data.dao.PlanDao
import com.oleg1202000.finapp.data.dao.SummaryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


 class LocalRepositoryModule @Inject constructor(

    private val categoriesDao: CategoriesDao,
    private val summaryDao: SummaryDao,
    private val planDao: PlanDao
 ) {


     // Categories table
    fun getCategries(isIncome: Boolean) =
        categoriesDao.getCategories(isIncome).flowOn(Dispatchers.IO)

     suspend fun setCategory (category: Category) = categoriesDao.setCategory(category)

     suspend fun deleteCategory (category: Category) = categoriesDao.deleteCategory(category)


     // Planned table
    fun getPlan(
         isIncome: Boolean,
         beginDate: Long,
         endDate: Long
     ) = planDao.getPlan(isIncome, beginDate, endDate).flowOn(Dispatchers.IO)

    suspend fun setPlan (plan: Planned) = planDao.setPlan(plan)

     suspend fun deletePlan (plan: Planned) = planDao.deletePlan(plan)


     // Summary table
    fun getSumAmount(
        isIncome: Boolean = false,
        beginDate: Long,
        endDate: Long
    ) = summaryDao.getSumAmount(isIncome, beginDate, endDate).flowOn(Dispatchers.IO)

    fun getHistory(
        beginDate: Long,
        endDate: Long
    ) = summaryDao.getHistory(endDate, beginDate).flowOn(Dispatchers.IO)

     suspend fun setSummary(summary: Summary) = summaryDao.setSummary(summary)

     suspend fun deleteSummary(id: Long) = summaryDao.deleteSummaryById(id)

     suspend fun updateSummary(summary: Summary) = summaryDao.updateSummary(summary)
}