package com.mk1morebugs.finapp.data

import com.mk1morebugs.finapp.data.local.room.CostForUi
import com.mk1morebugs.finapp.data.local.room.CostHistory
import com.mk1morebugs.finapp.data.local.room.Cost
import com.mk1morebugs.finapp.data.local.room.dao.CostsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeCostsDao : CostsDao {
    override fun getCostsForUi(
        isIncome: Boolean,
        isPlanned: Boolean,
        beginDate: Long,
        endDate: Long,
    ): Flow<List<CostForUi>> = flow {
        emit(
            fakeCategories.map {
                CostForUi(
                    categoryName = it.name,
                    iconColor = it.color,
                    iconId = it.iconId,
                    summaryAmount = fakeSummaries.sumOf { summary -> summary.amount },
                )
            }
        )
    }

    override fun getCostsHistory(
        beginDate: Long,
        endDate: Long,
        isPlanned: Boolean,
    ): Flow<List<CostHistory>> = flow {
        emit(fakeSummaries.map { summary ->

            val category = fakeCategories.find { category ->
                summary.id == category.id
            }

            CostHistory(
                id = summary.id,
                categoryName = category!!.name,
                isIncome = category.isIncome,
                iconId = category.iconId,
                iconColor = category.color,
                amount = summary.amount,
                date = summary.date,
                about = summary.about,
            )
        })
    }

    override suspend fun setCost(cost: Cost) {
        fakeSummaries.add(cost)
    }

    override suspend fun deleteCostById(id: Long) {
        fakeSummaries.remove(
            fakeSummaries.find {
                it.id == id
            }
        )
    }

    override suspend  fun updateCost(cost: Cost) {
        fakeSummaries.remove(
            fakeSummaries.find {
                it.id == cost.id
            }
        )
        fakeSummaries.add(cost)
    }
}