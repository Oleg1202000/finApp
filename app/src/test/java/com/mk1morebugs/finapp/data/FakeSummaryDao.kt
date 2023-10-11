package com.mk1morebugs.finapp.data

import com.mk1morebugs.finapp.data.database.ReturnSumAmount
import com.mk1morebugs.finapp.data.database.ReturnSummaryHistory
import com.mk1morebugs.finapp.data.database.Summary
import com.mk1morebugs.finapp.data.database.dao.SummaryDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeSummaryDao : SummaryDao {

    override fun getSumAmount(
        isIncome: Boolean,
        beginDate: Long,
        endDate: Long

    ) : Flow<List<ReturnSumAmount>> = flow {
        emit(
            fakeCategories.map {
                ReturnSumAmount(
                    categoryName = it.name,
                    color = it.color,
                    iconId = it.iconId,
                    amount = fakeSummaries.sumOf { summary -> summary.amount },
                    plan = fakePlans.sumOf { planned -> planned.amount },
                )
            }
        )
    }

    override fun getSummaryHistory(
        beginDate: Long,
        endDate: Long
    ) : Flow<List<ReturnSummaryHistory>> = flow {
        emit(fakeSummaries.map { summary ->

            val category = fakeCategories.find { category ->
                summary.id == category.id
            }

            ReturnSummaryHistory(
                id = summary.id,
                categoryName = category!!.name,
                isIncome = category.isIncome,
                iconId = category.iconId,
                color = category.color,
                amount = summary.amount,
                date = summary.date,
                about = summary.about,
            )
        })
    }

    override suspend fun setSummary(summary: Summary) {
        fakeSummaries.add(summary)
    }

    override suspend fun deleteSummaryById(id: Long) {
        fakeSummaries.remove(
            fakeSummaries.find {
                it.id == id
            }
        )
    }

    override suspend  fun updateSummary(summary: Summary) {
        fakeSummaries.remove(
            fakeSummaries.find {
                it.id == summary.id
            }
        )
        fakeSummaries.add(summary)
    }
}