package com.oleg1202000.finapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.oleg1202000.finapp.data.database.dao.CategoriesDao
import com.oleg1202000.finapp.data.database.dao.PlanDao
import com.oleg1202000.finapp.data.database.dao.SummaryDao


@Database (

    entities = [
        Category::class,
        Summary::class,
        Planned::class,
    ],

    version = 1,

    exportSchema = false
)

@TypeConverters(DateConverter::class)
abstract class FinappDatabase : RoomDatabase() {

    abstract fun categoriesDao(): CategoriesDao

    abstract fun summaryDao(): SummaryDao

    abstract fun planDao(): PlanDao
}
