package com.mk1morebugs.finapp.data

import com.mk1morebugs.finapp.data.database.Planned
import com.mk1morebugs.finapp.data.database.ReturnPlanAmount
import com.mk1morebugs.finapp.data.database.ReturnPlannedHistory
import com.mk1morebugs.finapp.data.database.dao.PlanDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePlanDao : PlanDao {

    override fun getPlan(
        isIncome: Boolean,
        beginDate: Long,
        endDate: Long
    ) : Flow<List<ReturnPlanAmount>> = flow {
        emit(
            fakeCategories.map {
                ReturnPlanAmount(
                    categoryName = it.name,
                    color = it.color,
                    iconId = it.iconId,
                    amount = fakeSummaries.sumOf { summary -> summary.amount },
                    plan = fakePlans.sumOf { planned -> planned.amount },
                )
            }

        )
    }

    override fun getPlannedHistory(
        beginDate: Long,
        endDate: Long
    ) : Flow<List<ReturnPlannedHistory>> = flow {
        emit(fakePlans.map { planned ->

            val category = fakeCategories.find { category ->
                planned.id == category.id
            }

            ReturnPlannedHistory(
                id = planned.id,
                categoryName = category!!.name,
                iconId = category.iconId,
                color = category.color,
                amount = planned.amount,
                date = planned.date,
            )
        })
    }

    override suspend fun setPlan(planned: Planned) {
        fakePlans.add(planned)
    }

    override suspend fun deletePlanById(id: Long) {
        fakePlans.remove(
            fakePlans.find {
                it.id == id
            }
        )
    }
}