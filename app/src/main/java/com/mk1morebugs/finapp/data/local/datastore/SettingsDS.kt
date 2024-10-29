package com.mk1morebugs.finapp.data.local.datastore

interface SettingsDS {
    suspend fun getIsFirstLaunch(): Boolean

    suspend fun setIsFirstLaunch(value: Boolean)
}