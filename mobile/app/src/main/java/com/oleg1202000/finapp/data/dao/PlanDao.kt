package com.oleg1202000.finapp.data.dao

import androidx.room.*
import com.oleg1202000.finapp.data.Plan
import com.oleg1202000.finapp.data.ReturnPlanName
import java.sql.Date

@Dao
interface PlanDao {
    @Query(
        """
        SELECT categories.name AS category_name, SUM(plan.amount) AS plan_amount
        
        FROM plan
        
        INNER JOIN categories ON categories.id = plan.category_id
        
        WHERE plan.date >= :beginDate AND plan.date <= :endDate
        
        GROUP BY plan.category_id
        """
    )
    fun getPlan(beginDate: Date?, endDate: Date?): List<ReturnPlanName>

    @Insert
    fun setPlan(plan: Plan)


    @Delete
    fun deletePlan(plan: Plan)


    @Update
    fun updPlan(plan: Plan)
}
