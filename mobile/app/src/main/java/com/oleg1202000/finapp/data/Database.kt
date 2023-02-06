package com.oleg1202000.finapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database (
    entities = [
        Users::class,
        Targets::class,
        Categories::class,
        SubCategories:: class,
        Tags::class,
        Accounts::class,
        SummaryTags::class,
        SubCategoriesCategories::class,
        UsersCategories::class
    ],
    version = 1,
    exportSchema = false
        )
abstract class FinappDatabase : RoomDatabase() {
    // abstract fun usersDAO(): UsersDAO
}