package com.mk1morebugs.finapp.data

import com.mk1morebugs.finapp.data.local.room.Category
import com.mk1morebugs.finapp.data.local.room.CategoryWithoutIsIncome
import com.mk1morebugs.finapp.data.local.room.CostForUi
import com.mk1morebugs.finapp.data.local.room.CostHistory
import com.mk1morebugs.finapp.data.local.room.Cost
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository : Repository {

    private var fakeCategories: MutableList<Category> = mutableListOf()
    var fakeCost: MutableList<Cost> = mutableListOf()


    override fun getCategories(isIncome: Boolean): Flow<List<CategoryWithoutIsIncome>> = flow {
        emit(
            fakeCategories.map {
                CategoryWithoutIsIncome(
                    id = it.id,
                    name = it.name,
                    iconColor = it.color,
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

    override fun getCosts(
        isIncome: Boolean,
        isPlanned: Boolean,
        beginDate: Long,
        endDate: Long,
    ): Flow<List<CostForUi>> {
        TODO("Not yet implemented")
    }

    override fun getCostsHistory(
        beginDate: Long,
        endDate: Long,
        isPlanned: Boolean,
    ): Flow<List<CostHistory>> {
        TODO("Not yet implemented")
    }

    override suspend fun setCost(cost: Cost) {
        fakeCost.add(cost)
    }

    override suspend fun deleteCostById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun updateCost(cost: Cost) {
        TODO("Not yet implemented")
    }

}