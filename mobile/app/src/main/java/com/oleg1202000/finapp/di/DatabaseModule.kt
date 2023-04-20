package com.oleg1202000.finapp.di

import android.content.Context
import androidx.room.Room
import com.oleg1202000.finapp.data.FinappDatabase
import com.oleg1202000.finapp.data.dao.CategoriesDao
import com.oleg1202000.finapp.data.dao.PlanDao
import com.oleg1202000.finapp.data.dao.SummaryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext context: Context):FinappDatabase = Room.databaseBuilder(
        context.applicationContext, FinappDatabase::class.java, "finappdatabase.db"
        )
        .build()

    @Singleton
    @Provides
    fun provideCategoriesDao(db: FinappDatabase): CategoriesDao = db.categoriesDao()

    @Singleton
    @Provides
    fun providePlanDao(db: FinappDatabase): PlanDao = db.planDao()

    @Singleton
    @Provides
    fun provideSummaryDao(db: FinappDatabase): SummaryDao = db.summaryDao()
}
