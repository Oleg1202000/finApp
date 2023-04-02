package com.oleg1202000.finapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FinappAplication : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}