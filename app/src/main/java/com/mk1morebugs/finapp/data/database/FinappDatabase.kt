package com.mk1morebugs.finapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mk1morebugs.finapp.data.database.dao.CategoriesDao
import com.mk1morebugs.finapp.data.database.dao.PlanDao
import com.mk1morebugs.finapp.data.database.dao.SummaryDao


@Database (

    entities = [
        Category::class,
        Summary::class,
        Planned::class,
    ],

    version = 1,

    exportSchema = false
)

abstract class FinappDatabase : RoomDatabase() {

    abstract fun categoriesDao(): CategoriesDao

    abstract fun summaryDao(): SummaryDao

    abstract fun planDao(): PlanDao
}
