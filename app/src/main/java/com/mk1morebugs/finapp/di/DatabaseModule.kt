package com.mk1morebugs.finapp.di

import android.content.Context
import androidx.room.Room
import com.mk1morebugs.finapp.data.database.FinappDatabase
import com.mk1morebugs.finapp.data.database.dao.CategoriesDao
import com.mk1morebugs.finapp.data.database.dao.PlanDao
import com.mk1morebugs.finapp.data.database.dao.SummaryDao
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
    fun provideLocalDatabase(@ApplicationContext context: Context): FinappDatabase = Room.databaseBuilder(
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
