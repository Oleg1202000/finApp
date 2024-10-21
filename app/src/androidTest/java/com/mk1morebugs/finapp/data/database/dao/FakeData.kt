package com.mk1morebugs.finapp.data.database.dao

import com.mk1morebugs.finapp.data.local.room.Category
import com.mk1morebugs.finapp.data.local.room.Planned
import com.mk1morebugs.finapp.data.local.room.ReturnPlanAmount
import com.mk1morebugs.finapp.data.local.room.ReturnPlannedHistory
import com.mk1morebugs.finapp.data.local.room.ReturnSumAmount
import com.mk1morebugs.finapp.data.local.room.ReturnSummaryHistory
import com.mk1morebugs.finapp.data.local.room.Summary

val fakeCategories: MutableList<Category> = mutableListOf(
    Category(
        id = 1L,
        name = "test name 1",
        isIncome = false,
        color = 0xFFD32F2F,
        iconId = 0,
    ),
    Category(
        id = 2L,
        name = "test name 2",
        isIncome = false,
        color = 0xFFD32F2F,
        iconId = 0,
    ),
)

val fakeSummaries: MutableList<Summary> = mutableListOf(
    Summary(
        id = 1L,
        categoryId = 1L,
        amount = 100,
        date = 0L,
        isSync = false,
        about = null,
    ),
    Summary(
        id = 2L,
        categoryId = 1L,
        amount = 111,
        date = 0L,
        isSync = false,
        about = "fake about",
    ),
    Summary(
        id = 3L,
        categoryId = 1L,
        amount = 200,
        date = 0L,
        isSync = false,
        about = null,
    ),
)

val fakePlans: MutableList<Planned> = mutableListOf(
    Planned(
        id = 1L,
        categoryId = 1L,
        amount = 500,
        date = 1L,
    ),
    Planned(
        id = 2L,
        categoryId = 2L,
        amount = 200,
        date = 0L,
    ),
)

val insertedCategory = Category(
    name = "test 1",
    isIncome = false,
    color = 0xFFD32F2F,
    iconId = 0,
)


val plannedValue = listOf(
        ReturnPlanAmount(categoryName = "test name 2", color = 4292030255, iconId = 0, amount = null, plan = 200),
        ReturnPlanAmount(categoryName = "test name 1", color = 4292030255, iconId = 0, amount = 411, plan = 500),
    )

val plannedHistory = listOf(
    ReturnPlannedHistory(id = 1, categoryName="test name 1", iconId = 0, color = 4292030255, amount = 500, date = 1),
    ReturnPlannedHistory(id = 2, categoryName="test name 2", iconId = 0, color = 4292030255, amount = 200, date = 0),
)


val factSummaryAmount = listOf(
    ReturnSumAmount(
        categoryName = "test name 1", color = 0xFFD32F2F, iconId = 0, amount = 411, plan = 500),
)

val factSummaryHistory = listOf(
    ReturnSummaryHistory(id = 1, categoryName = "test name 1", isIncome = false, iconId = 0, color = 4292030255, amount = 100, date = 0, about = null),
    ReturnSummaryHistory(id = 2, categoryName = "test name 1", isIncome = false, iconId = 0, color = 4292030255, amount = 111, date = 0, about = "fake about"),
    ReturnSummaryHistory(id = 3, categoryName = "test name 1", isIncome = false, iconId = 0, color = 4292030255, amount = 200, date = 0, about = null),
)

val newSummaryItem = Summary(
    id = 1L,
    categoryId = 1L,
    amount = 100,
    date = 0L,
    isSync = false,
    about = "update item",
)