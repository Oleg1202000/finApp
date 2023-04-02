package com.oleg1202000.finapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.oleg1202000.finapp.data.dao.CategoriesDao
import com.oleg1202000.finapp.data.dao.PlanDao
import com.oleg1202000.finapp.data.dao.SummaryDao
import com.oleg1202000.finapp.data.dao.TagsDao


@Database (
    entities = [
        Categories::class,
        Tags::class,
        Summary::class,
        Plan::class,
        SummaryTags::class

    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class FinappDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun tagsDao(): TagsDao
    abstract fun summaryDao(): SummaryDao
    abstract fun planDao(): PlanDao
}
