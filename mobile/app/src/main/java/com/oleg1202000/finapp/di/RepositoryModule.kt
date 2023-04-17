package com.oleg1202000.finapp.di

import com.oleg1202000.finapp.data.*
import com.oleg1202000.finapp.data.dao.CategoriesDao
import com.oleg1202000.finapp.data.dao.PlanDao
import com.oleg1202000.finapp.data.dao.SummaryDao
import com.oleg1202000.finapp.data.dao.TagsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.sql.Date
import javax.inject.Inject


 class RepositoryModule @Inject constructor(

    private val categoriesDao: CategoriesDao,
    private val summaryDao: SummaryDao,
    private val tagsDao: TagsDao,
    private val planDao: PlanDao
 ) {

    fun getAllCategries() = flow { emit(categoriesDao.getCategories())}.flowOn(Dispatchers.IO)
    fun setCategory (category: Categories) = categoriesDao.setCategory(category)


    fun deleteCategory (category: Categories) = categoriesDao.deleteCategory(category)
    // TODO: Добавить emit

    fun getPlan(beginDate: Date?, endDate: Date?) = flow { emit(planDao.getPlan(beginDate, endDate)) }
        .flowOn(Dispatchers.IO)
    fun setPlan (plan: Planned) = planDao.setPlan(plan)
     fun deletePlan (plan: Planned) = planDao.deletePlan(plan)
     fun updPlan (plan: Planned) = planDao.updPlan(plan)


    fun getSumAmount(
        categoryIds: List<Long>,
        tagsIds: List<Long>,
        beginDate: Date?,
        endDate: Date?
    ) = flow { emit(summaryDao.getSumAmount(categoryIds,tagsIds, beginDate, endDate)) }
        .flowOn(Dispatchers.IO)

     fun getSumAmountAndPlan(
         categoryIds: List<Long>,
         tagsIds: List<Long>,
         beginDate: Date?,
         endDate: Date?
     ) = flow { emit(summaryDao.getSumAmountAndPlan(categoryIds,tagsIds, beginDate, endDate)) }
         .flowOn(Dispatchers.IO)
    fun getHistory(
        tagIds: List<Long>,
        categoryIds: List<Long>,
        endDate: Date?,
        beginDate: Date?
    ) = summaryDao.getHistory(tagIds, categoryIds, endDate, beginDate)
    fun setSummary(summary: Summary) = summaryDao.setSummary(summary)
    fun deleteSummary(id: Long) = summaryDao.deleteSummaryById(id)
    fun updateSummary(summary: Summary) = summaryDao.updateSummary(summary)

    fun getTags() = flow { emit(tagsDao.getTags()) }
        .flowOn(Dispatchers.IO)
    fun setTag(tag: Tags) = tagsDao.setTag(tag)
    fun deleteTag(tag: Tags) = tagsDao.deleteTag(tag)
}