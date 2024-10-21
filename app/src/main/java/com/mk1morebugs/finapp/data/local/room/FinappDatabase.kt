package com.mk1morebugs.finapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mk1morebugs.finapp.data.local.room.dao.CategoriesDao
import com.mk1morebugs.finapp.data.local.room.dao.PlanDao
import com.mk1morebugs.finapp.data.local.room.dao.SummaryDao

@Database (
    entities = [
        Category::class,
        Summary::class,
        Planned::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class FinappDatabase : RoomDatabase() {

    abstract fun categoriesDao(): CategoriesDao

    abstract fun summaryDao(): SummaryDao

    abstract fun planDao(): PlanDao
}
