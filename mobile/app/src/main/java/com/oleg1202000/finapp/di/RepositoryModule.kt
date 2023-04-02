package com.oleg1202000.finapp.di

import com.oleg1202000.finapp.data.*
import com.oleg1202000.finapp.data.dao.CategoriesDao
import com.oleg1202000.finapp.data.dao.PlanDao
import com.oleg1202000.finapp.data.dao.SummaryDao
import com.oleg1202000.finapp.data.dao.TagsDao
import kotlinx.coroutines.flow.flow
import java.sql.Date
import javax.inject.Inject


 class RepositoryModule @Inject constructor(

    private val categoriesDao: CategoriesDao,
    private val planDao: PlanDao,
    private val summaryDao: SummaryDao,
    private val tagsDao: TagsDao
) {

    fun getAllCategries() = flow { emit(categoriesDao.getCategories()) }
    fun setCategory (category: Categories) = categoriesDao.setCategory(category)


    fun deleteCategory (category: Categories) = categoriesDao.deleteCategory(category)
    // TODO: Добавить emit

    fun getPlan(beginDate: Date?, endDate: Date?) = planDao.getPlan(beginDate, endDate)
    fun setPlan (plan: Plan) = planDao.setPlan(plan)
     fun deletePlan (plan: Plan) = planDao.deletePlan(plan)
     fun updPlan (plan: Plan) = planDao.updPlan(plan)


    fun getSumAmount(
        categoryIds: List<Long>,
        tagsIds: List<Long>,
        beginDate: Date?,
        endDate: Date?
    ) = summaryDao.getSumAmount(categoryIds,tagsIds, beginDate, endDate)
    fun getHistory(
        tagIds: List<Long>,
        categoryIds: List<Long>,
        endDate: Date?,
        beginDate: Date?
    ) = summaryDao.getHistory(tagIds, categoryIds, endDate, beginDate)
    fun setSummary(summary: Summary) = summaryDao.setSummary(summary)
    fun deleteSummary(id: Long) = summaryDao.deleteSummaryById(id)
    fun updateSummary(summary: Summary) = summaryDao.updateSummary(summary)

    fun getTags() = tagsDao.getTags()
    fun setTag(tag: Tags) = tagsDao.setTag(tag)
    fun deleteTag(tag: Tags) = tagsDao.deleteTag(tag)
}