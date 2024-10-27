package com.mk1morebugs.finapp.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map


class SettingsDSImpl(
    private val dataStore: DataStore<Preferences>
) : SettingsDS {
    private val isFirstLaunchKey = booleanPreferencesKey("is_first_launch")

    override suspend fun getIsFirstLaunch(): Boolean {
        val isFirstLaunch = dataStore.data.map { preferences ->
            preferences[isFirstLaunchKey]
        }.firstOrNull() ?: true
        return isFirstLaunch
    }

    override suspend fun setIsFirstLaunch(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[isFirstLaunchKey] = value
        }
    }
}