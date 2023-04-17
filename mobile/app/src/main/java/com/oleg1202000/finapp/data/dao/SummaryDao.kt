package com.oleg1202000.finapp.data.dao

import androidx.room.*
import com.oleg1202000.finapp.data.*
import java.sql.Date

@Dao
interface SummaryDao {

    @Query(
        """
        SELECT categories.name AS category_name, SUM(summary.amount) AS summary_amount, SUM(planned.amount) AS planned
        
        FROM summary

        JOIN categories ON categories.id = summary.category_id
        LEFT JOIN planned ON planned.category_id = categories.id

        WHERE summary.date >= :beginDate AND summary.date <= :endDate AND
        categories.is_income = :isIncome
        
        GROUP BY summary.category_id
        ORDER BY SUM(summary.amount) DESC
        
        """
    )
    fun getSumAmount(
        isIncome: Boolean = false,
        beginDate: Date?,
        endDate: Date?

    ) : List<ReturnSumAmount>


    @Query(
        """
        SELECT summary.id, categories.name, summary.amount, summary.date, summary.about
        
        FROM summary

        INNER JOIN categories ON categories.id = summary.category_id

        WHERE summary.date >= :beginDate AND summary.date <= :endDate
        
        ORDER BY summary.date DESC
        """
    )
    fun getHistory(
        endDate: Date?,
        beginDate: Date?

    ) : List<ReturnHistory>


    @Insert
    fun setSummary(summary: Summary)


    //delete
    @Query(
        """
        DELETE FROM summary
        WHERE id = :id
        """
    )
    fun deleteSummaryById(id: Long)


    @Update
    fun updateSummary(summary: Summary)
}
