package com.oleg1202000.finapp.di

import com.oleg1202000.finapp.data.Categories
import com.oleg1202000.finapp.data.Plan
import com.oleg1202000.finapp.data.Summary
import com.oleg1202000.finapp.data.Tags
import com.oleg1202000.finapp.data.dao.CategoriesDao
import com.oleg1202000.finapp.data.dao.PlanDao
import com.oleg1202000.finapp.data.dao.SummaryDao
import com.oleg1202000.finapp.data.dao.TagsDao
import dagger.Module
import javax.inject.Inject


@Module(includes = [DatabaseModule::class])
 class RepositoryModule @Inject constructor(

    private val categoriesDao: CategoriesDao,
    private val planDao: PlanDao,
    private val summaryDao: SummaryDao,
    private val tagsDao: TagsDao
){

    fun getAllCategries() = categoriesDao.getCategories()
    fun addCategory (сategory: Categories) = categoriesDao.addCategory(сategory)
    fun deleteCategory (сategory: Categories) = categoriesDao.deleteCategory(сategory)

    fun getPlan(beginDate: String, endDate: String) = planDao.getPlan(beginDate, endDate)
    fun addPlan (plan: Plan) = planDao.addPlan(plan)


    fun getSumAmount(
        categoryIds: List<ULong>,
        tagsIds: List<ULong>,
        beginDate: String,
        endDate: String
    ) = summaryDao.getSumAmount(categoryIds,tagsIds, beginDate, endDate)
    fun getHistory(
        tagIds: List<ULong>,
        categoryIds: List<ULong>,
        endDate: String,
        beginDate: String
    ) = summaryDao.getHistory(tagIds, categoryIds, endDate, beginDate)
    fun addSummary(summary: Summary) = summaryDao.addSummary(summary)
    fun deleteSummary(id: ULong) = summaryDao.deleteSummary(id)
    fun updateSummary(summary: Summary) = summaryDao.updateSummary(summary)

    fun getTags() = tagsDao.getTags()
    fun addTag(tag: Tags) = tagsDao.addTag(tag)
    fun deleteTag(tag: Tags) = tagsDao.deleteTag(tag)
}
