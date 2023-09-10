package com.oleg1202000.finapp.di

import javax.inject.Singleton

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModules {
    @Singleton
    @Binds
    abstract fun bindRepository (repository: Repository): IRepository
}
