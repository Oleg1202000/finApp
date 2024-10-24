package com.mk1morebugs.finapp.data.database.dao

import com.mk1morebugs.finapp.data.local.room.Category
import com.mk1morebugs.finapp.data.local.room.CostForUi
import com.mk1morebugs.finapp.data.local.room.CostHistory
import com.mk1morebugs.finapp.data.local.room.Cost

val fakeCategories: List<Category> = listOf(
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
    Category(
        id = 3L,
        name = "test name 3",
        isIncome = true,
        color = 0xFFD32F2F,
        iconId = 0,
    ),
)

val fakeCosts: List<Cost> = listOf(
    Cost(
        id = 1L,
        categoryId = 1L,
        amount = 100,
        date = 0L,
        isPlanned = false,
        about = null,
    ),
    Cost(
        id = 2L,
        categoryId = 1L,
        amount = 111,
        date = 1L,
        isPlanned = false,
        about = "fake about",
    ),
    Cost(
        id = 3L,
        categoryId = 1L,
        amount = 200,
        date = 2L,
        isPlanned = false,
        about = "fake about",
    ),
    Cost(
        id = 4L,
        categoryId = 1L,
        amount = 200,
        date = 3L,
        isPlanned = true,
        about = null,
    ),
    Cost(
        id = 5L,
        categoryId = 3L,
        amount = 40000,
        date = 3L,
        isPlanned = false,
        about = null,
    ),
    Cost(
        id = 6L,
        categoryId = 1L,
        amount = 200,
        date = 5L,
        isPlanned = false,
        about = null,
    ),
    Cost(
        id = 7L,
        categoryId = 2L,
        amount = 400,
        date = 4L,
        isPlanned = false,
        about = null,
    ),
)