package com.oleg1202000.finapp.data.dao

import androidx.room.*
import com.oleg1202000.finapp.data.Planned
import com.oleg1202000.finapp.data.ReturnPlanName
import java.sql.Date

@Dao
interface PlanDao {
    @Query(
        """
        SELECT categories.name AS category_name, SUM(planned.amount) AS plan_amount
        
        FROM planned
        
        INNER JOIN categories ON categories.id = planned.category_id
        
        WHERE planned.date >= :beginDate AND planned.date <= :endDate
        
        GROUP BY planned.category_id
        """
    )
    fun getPlan(beginDate: Date?, endDate: Date?): List<ReturnPlanName>

    @Insert
    fun setPlan(planned: Planned)


    @Delete
    fun deletePlan(planned: Planned)


    @Update
    fun updPlan(planned: Planned)
}
