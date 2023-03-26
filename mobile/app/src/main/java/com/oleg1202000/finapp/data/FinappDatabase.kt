package com.oleg1202000.finapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oleg1202000.finapp.data.dao.CategoriesDao
import com.oleg1202000.finapp.data.dao.PlanDAO
import com.oleg1202000.finapp.data.dao.SummaryDao
import com.oleg1202000.finapp.data.dao.TagsDao



@Database (
    entities = [
        Categories::class,
        Tags::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FinappDatabase : RoomDatabase() {
    abstract fun categoriesDAO(): CategoriesDao
    abstract fun tagsDAO(): TagsDao
    abstract fun summaryDAO(): SummaryDao

    abstract fun planDAO(): PlanDAO
}

private lateinit var applicationContext: Context
val db = Room.databaseBuilder(
    context = applicationContext, FinappDatabase::class.java, "finappDatabase.db"
).build()