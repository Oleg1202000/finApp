package com.oleg1202000.finapp.di

import com.oleg1202000.finapp.ui.MainActivity
import dagger.Component


@Component(modules = [RepositoryModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)

}