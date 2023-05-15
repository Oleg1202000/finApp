package com.oleg1202000.finapp.data.dao

import androidx.room.*
import com.oleg1202000.finapp.data.*
import kotlinx.coroutines.flow.Flow


@Dao
interface SummaryDao {

    @Query(
        """
        SELECT 
        categories.name AS category_name,
        categories.color AS color, 
        categories.icon_id AS icon_id, 
        SUM(summary.amount) AS summary_amount,
        nested_planned.planned_amount AS planned_amount

        
        FROM summary
        
        JOIN categories ON categories.id = summary.category_id
        LEFT JOIN 
        
        (SELECT
         
        categories.name AS category_name,
        SUM(planned.amount) AS planned_amount
        
        FROM  planned
        
        JOIN categories ON categories.id = planned.category_id
        
        WHERE planned.date >= :beginDate AND planned.date <= :endDate AND
        categories.is_income = :isIncome
        
        GROUP BY categories.id
        ORDER BY planned_amount DESC
         
         
         ) AS nested_planned ON nested_planned.category_name = categories.name
        
        WHERE summary.date >= :beginDate AND summary.date <= :endDate AND
        categories.is_income = :isIncome
        
        GROUP BY categories.id
        ORDER BY summary_amount DESC
        """
    )
    fun getSumAmount(
        isIncome: Boolean = false,
        beginDate: Long,
        endDate: Long

    ) : Flow<List<ReturnSumAmount>>


    @Query(
        """
        SELECT 
        summary.id, 
        categories.name,
        categories.is_income,
        categories.icon_id, 
        categories.color, 
        summary.amount, 
        summary.date, 
        summary.about
        
        FROM summary

        INNER JOIN categories ON categories.id = summary.category_id

        WHERE summary.date >= :beginDate AND summary.date <= :endDate
        
        ORDER BY summary.date DESC
        """
    )
    fun getHistory(
        beginDate: Long,
        endDate: Long

    ) : Flow<List<ReturnSummaryHistory>>


    @Insert
     suspend fun setSummary(summary: Summary)


    //delete
    @Query(
        """
        DELETE FROM summary
        WHERE id = :id
        """
    )
    suspend fun deleteSummaryById(id: Long)


    @Update
    suspend  fun updateSummary(summary: Summary)
}
