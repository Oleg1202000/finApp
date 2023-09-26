package com.oleg1202000.finapp.data.database.dao

import androidx.room.*
import com.oleg1202000.finapp.data.database.Planned
import com.oleg1202000.finapp.data.database.ReturnPlanAmount
import com.oleg1202000.finapp.data.database.ReturnPlannedHistory
import kotlinx.coroutines.flow.Flow


@Dao
interface PlanDao {
    @Query(
        """
        SELECT 
        categories.name AS category_name,
        categories.color AS color, 
        categories.icon_id AS icon_id, 
        SUM(planned.amount) AS planned_amount,
        nested_summary.summary_amount AS summary_amount

        
        FROM planned
        
        JOIN categories ON categories.id = planned.category_id
        LEFT JOIN 
        
        (SELECT
         
        categories.name AS category_name,
        SUM(summary.amount) AS summary_amount
        
        FROM  summary
        
        JOIN categories ON categories.id = summary.category_id
        
        WHERE summary.date >= :beginDate AND summary.date <= :endDate AND
        categories.is_income = :isIncome
        
        GROUP BY categories.id
         
         
         ) AS nested_summary ON nested_summary.category_name = categories.name
        
        WHERE planned.date >= :beginDate AND planned.date <= :endDate AND
        categories.is_income = :isIncome
        
        GROUP BY categories.id
        ORDER BY planned_amount, summary_amount DESC
        """
    )

    // LEFT JOIN summary ON categories.id = summary.category_id
    fun getPlan(
        isIncome: Boolean = false,
        beginDate: Long,
        endDate: Long

    ) : Flow<List<ReturnPlanAmount>>



    @Query(
        """
        SELECT 
        planned.id, 
        categories.name, 
        categories.icon_id, 
        categories.color, 
        planned.amount, 
        planned.date
        
        FROM planned

        INNER JOIN categories ON categories.id = planned.category_id

        WHERE planned.date >= :beginDate AND planned.date <= :endDate
        
        ORDER BY planned.date DESC
        """
    )
    fun getHistory(
        beginDate: Long,
        endDate: Long

    ) : Flow<List<ReturnPlannedHistory>>

    @Insert
    suspend fun setPlan(planned: Planned)


    @Query(
        """
        DELETE FROM planned
        WHERE id = :id
        """
    )
    suspend fun deletePlanById(id: Long)
}
