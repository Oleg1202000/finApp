package com.mk1morebugs.finapp.data

import com.oleg1202000.finapp.R
import com.mk1morebugs.finapp.data.database.Category
import com.mk1morebugs.finapp.data.database.Planned
import com.mk1morebugs.finapp.data.database.Summary


val fakeCategories: MutableList<Category> = mutableListOf(
    Category(
        id = 0L,
        name = "test name",
        isIncome = false,
        color = 0xFFD32F2F,
        iconId = R.drawable.ic_category_apartment_40px,
    ),
    Category(
        id = 1L,
        name = "test name",
        isIncome = true,
        color = 0xFFD32F2F,
        iconId = R.drawable.ic_category_apartment_40px,
    ),
)

val fakeSummaries: MutableList<Summary> = mutableListOf(
    Summary(
        id = 0L,
        categoryId = 0L,
        amount = 100,
        date = 0L,
        isSync = false,
        about = null,
    ),
    Summary(
        id = 1L,
        categoryId = 0L,
        amount = 111,
        date = 0L,
        isSync = false,
        about = "fake about",
    ),
    Summary(
        id = 2L,
        categoryId = 1L,
        amount = 200,
        date = 0L,
        isSync = false,
        about = null,
    ),
)

val fakePlans: MutableList<Planned> = mutableListOf(
    Planned(
        id = 0L,
        categoryId = 0L,
        amount = 500,
        date = 0L,
    ),
    Planned(
        id = 1L,
        categoryId = 1L,
        amount = 200,
        date = 0L,
    ),
)

val fakeDeletedCategory = Category(
    id = 3L,
    name = "test name 3",
    isIncome = true,
    color = 0xFFD32F2F,
    iconId = R.drawable.ic_category_apartment_40px,
)
