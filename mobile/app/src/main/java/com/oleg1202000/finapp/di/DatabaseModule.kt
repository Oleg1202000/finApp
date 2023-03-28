package com.oleg1202000.finapp.di

import android.content.Context
import androidx.room.Room
import com.oleg1202000.finapp.data.FinappDatabase
import com.oleg1202000.finapp.data.dao.CategoriesDao
import com.oleg1202000.finapp.data.dao.PlanDao
import com.oleg1202000.finapp.data.dao.SummaryDao
import com.oleg1202000.finapp.data.dao.TagsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(context: Context):FinappDatabase = Room.databaseBuilder(
        context, FinappDatabase::class.java, "finappDatabase.db"
        ).build()

    @Provides
    @Singleton
    fun provideCategoriesDao(db: FinappDatabase): CategoriesDao = db.categoriesDao()

    @Provides
    @Singleton
    fun providePlanDao(db: FinappDatabase): PlanDao = db.planDao()

    @Provides
    @Singleton
    fun provideSummaryDao(db: FinappDatabase): SummaryDao = db.summaryDao()

    @Provides
    @Singleton
    fun provideTagsDao(db: FinappDatabase): TagsDao = db.tagsDao()
}
