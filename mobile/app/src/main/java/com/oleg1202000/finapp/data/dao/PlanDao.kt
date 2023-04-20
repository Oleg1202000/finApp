package com.oleg1202000.finapp.data.dao

import androidx.room.*
import com.oleg1202000.finapp.data.Planned
import com.oleg1202000.finapp.data.ReturnSumAmount
import java.util.Date


@Dao
interface PlanDao {
    @Query(
        """
        SELECT categories.name AS category_name, categories.color AS color, categories.path_to_icon AS path_to_icon, SUM(summary.amount) AS summary_amount, SUM(planned.amount) AS planned
        
        FROM planned
        
        JOIN categories ON categories.id = planned.category_id
        JOIN summary ON categories.id = summary.category_id
        
        WHERE planned.date >= :beginDate AND planned.date <= :endDate AND
        categories.is_income = :isIncome
        
        GROUP BY planned.category_id
        """
    )
    fun getPlan(
        isIncome: Boolean = false,
        beginDate: Date,
        endDate: Date

    ) : List<ReturnSumAmount>

    @Update
    fun setPlan(planned: Planned)


    @Delete
    fun deletePlan(planned: Planned)
}
