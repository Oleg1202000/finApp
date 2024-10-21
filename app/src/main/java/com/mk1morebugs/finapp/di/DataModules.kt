package com.mk1morebugs.finapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.mk1morebugs.finapp.data.Repository
import com.mk1morebugs.finapp.data.RepositoryImpl
import com.mk1morebugs.finapp.data.local.room.FinappDatabase
import com.mk1morebugs.finapp.data.local.room.dao.CategoriesDao
import com.mk1morebugs.finapp.data.local.room.dao.PlanDao
import com.mk1morebugs.finapp.data.local.room.dao.SummaryDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindRepository (repository: RepositoryImpl): Repository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext context: Context): FinappDatabase = Room.databaseBuilder(
        context.applicationContext, FinappDatabase::class.java, "finappdatabase.db"
    ).build()

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

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context) : DataStore<Preferences> =
        PreferenceDataStoreFactory.create (
            produceFile = {
                context.preferencesDataStoreFile("plan_settings")
            }
        )
}