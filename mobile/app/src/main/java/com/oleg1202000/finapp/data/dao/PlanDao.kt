package com.oleg1202000.finapp.data.dao

import androidx.room.*
import com.oleg1202000.finapp.data.Planned
import com.oleg1202000.finapp.data.ReturnSumAmount
import kotlinx.coroutines.flow.Flow


@Dao
interface PlanDao {
    @Query(
        """
        SELECT categories.name AS category_name, categories.color AS color, categories.icon_id AS icon_id, SUM(summary.amount) AS summary_amount, SUM(planned.amount) AS planned
        
        FROM planned
        
        JOIN categories ON categories.id = planned.category_id
        LEFT JOIN summary ON categories.id = summary.category_id
        
        WHERE planned.date >= :beginDate AND planned.date <= :endDate AND
        categories.is_income = :isIncome
        
        GROUP BY planned.category_id
        ORDER BY planned DESC
        """
    )
    fun getPlan(
        isIncome: Boolean = false,
        beginDate: Long,
        endDate: Long

    ) : Flow<List<ReturnSumAmount>>

    @Insert
    suspend fun setPlan(planned: Planned)


    @Delete
    suspend fun deletePlan(planned: Planned)
}
