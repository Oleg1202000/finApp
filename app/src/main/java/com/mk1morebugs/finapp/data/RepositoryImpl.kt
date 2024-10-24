package com.mk1morebugs.finapp.data

import com.mk1morebugs.finapp.data.local.room.Category
import com.mk1morebugs.finapp.data.local.room.Cost
import com.mk1morebugs.finapp.data.local.room.CostForUi
import com.mk1morebugs.finapp.data.local.room.CostHistory
import com.mk1morebugs.finapp.data.local.room.dao.CategoriesDao
import com.mk1morebugs.finapp.data.local.room.dao.CostsDao
import com.mk1morebugs.finapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val categoriesDao: CategoriesDao,
    private val costsDao: CostsDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : Repository {

    override fun getCategories (isIncome: Boolean) = categoriesDao.getCategories(isIncome)
        .flowOn(dispatcher)

    override suspend fun setCategory (category: Category) = categoriesDao.setCategory(category)

    override suspend fun deleteCategoryById (id: Long) = categoriesDao.deleteCategoryById(id)


    override fun getCosts(
        isIncome: Boolean,
        isPlanned: Boolean,
        beginDate: Long,
        endDate: Long,
    ): Flow<List<CostForUi>> {
        return costsDao.getCostsForUi(
            isIncome = isIncome,
            isPlanned = isPlanned,
            beginDate = beginDate,
            endDate = endDate,
        ).flowOn(dispatcher)
    }

    override fun getCostsHistory(
        beginDate: Long,
        endDate: Long,
        isPlanned: Boolean,
    ): Flow<List<CostHistory>> {
        return costsDao.getCostsHistory(
            beginDate = beginDate,
            endDate = endDate,
            isPlanned = isPlanned
        ).flowOn(dispatcher)
    }

    override suspend fun setCost(cost: Cost) = costsDao.setCost(cost)

    override suspend fun deleteCostById(id: Long) = costsDao.deleteCostById(id)

    override suspend fun updateCost(cost: Cost) = costsDao.updateCost(cost)
}