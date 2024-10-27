package com.mk1morebugs.finapp.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import com.google.common.truth.Truth.assertThat

class SettingsDSImplTest {
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var settingsDS: SettingsDS

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher + Job())

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Before
    fun setUp() {
        dataStore = PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { tmpFolder.newFile("settings.preferences_pb") }
        )
        settingsDS = SettingsDSImpl(dataStore = dataStore)
    }

    @Test
    fun getIsFirstLaunch_WhenLaunchIsFirst_ReturnTrue() = runTest(testDispatcher) {
        val isFirstLaunch = settingsDS.getIsFirstLaunch()

        assertThat(isFirstLaunch).isTrue()
    }

    @Test
    fun getIsFirstLaunch_WhenLaunchIsNotFirst_ReturnFalse() = runTest(testDispatcher)  {
        settingsDS.setIsFirstLaunch(value = false)

        val isFirstLaunch = settingsDS.getIsFirstLaunch()
        assertThat(isFirstLaunch).isFalse()
    }
}