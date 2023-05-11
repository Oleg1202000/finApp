package com.oleg1202000.finapp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.oleg1202000.finapp.data.Category
import com.oleg1202000.finapp.data.Planned
import com.oleg1202000.finapp.data.Summary
import com.oleg1202000.finapp.data.dao.CategoriesDao
import com.oleg1202000.finapp.data.dao.PlanDao
import com.oleg1202000.finapp.data.dao.SummaryDao
import com.oleg1202000.finapp.ui.graphdraw.GraphPeriod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class LocalRepositoryModule @Inject constructor(

    private val categoriesDao: CategoriesDao,
    private val summaryDao: SummaryDao,
    private val planDao: PlanDao,
    private val dataPreferences: DataStore<Preferences>
 ) {


     // Categories table
    fun getCategries (isIncome: Boolean) =
        categoriesDao.getCategories(isIncome).flowOn(Dispatchers.IO)

     suspend fun setCategory (category: Category) = categoriesDao.setCategory(category)

     suspend fun deleteCategoryById (id: Long) = categoriesDao.deleteCategoryById(id)


     // Planned table
    fun getPlan(
         isIncome: Boolean,
         beginDate: Long,
         endDate: Long
     ) = planDao.getPlan(isIncome, beginDate, endDate).flowOn(Dispatchers.IO)

    suspend fun setPlan (plan: Planned) = planDao.setPlan(plan)

     suspend fun deletePlanById (id: Long) = planDao.deletePlanById(id)

    fun getPlannedHistory(
        beginDate: Long,
        endDate: Long
    ) = planDao.getHistory(endDate, beginDate).flowOn(Dispatchers.IO)


     // Summary table
    fun getSumAmount(
        isIncome: Boolean = false,
        beginDate: Long,
        endDate: Long
    ) = summaryDao.getSumAmount(isIncome, beginDate, endDate).flowOn(Dispatchers.IO)

    fun getSummaryHistory(
        beginDate: Long,
        endDate: Long
    ) = summaryDao.getHistory(endDate, beginDate).flowOn(Dispatchers.IO)

     suspend fun setSummary(summary: Summary) = summaryDao.setSummary(summary)

     suspend fun deleteSummaryById(id: Long) = summaryDao.deleteSummaryById(id)

     suspend fun updateSummary(summary: Summary) = summaryDao.updateSummary(summary)


    // DataStore
     fun getDatePeriod(datePeriodKey: Preferences.Key<String> = stringPreferencesKey("plan_date_period")): Flow<GraphPeriod> = dataPreferences.data
         .map { preferences ->

             when (preferences[datePeriodKey]) {
                 "DAY" -> GraphPeriod.DAY
                 "WEEK" -> GraphPeriod.WEEK
                 else -> GraphPeriod.MONTH

             }

         }

    fun getLastUpdateDate(lastUpdateMillisKey: Preferences.Key<Long> = longPreferencesKey("last_update_millis")): Flow<Long> = dataPreferences.data
        .map { preferences ->
            // No type safety.
            preferences[lastUpdateMillisKey] ?: 0
        }

    suspend fun setDatePeriod(datePeriod: String) {
        dataPreferences.edit { plan ->
            plan[stringPreferencesKey("plan_date_period")] = datePeriod
        }
    }

    suspend fun setLastUpdateDate(lastUpdateMillis: Long) {
        dataPreferences.edit { plan ->
            plan[longPreferencesKey("last_update_millis")] = lastUpdateMillis
        }
    }

}