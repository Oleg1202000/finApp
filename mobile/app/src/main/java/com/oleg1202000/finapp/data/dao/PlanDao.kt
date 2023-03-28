package com.oleg1202000.finapp.data.dao

import androidx.room.Insert
import androidx.room.Query
import com.oleg1202000.finapp.data.Plan
import com.oleg1202000.finapp.data.ReturnPlanName

interface PlanDao {
    @Query(
        """
        SELECT categories.name, plan.limit
        
        FROM plan
        
        JOIN categories ON categories.id = plan.category_id
        
        WHERE plan.date >= :beginDate AND plan.date <= :endDate
        
        GROUP BY plan.category_id
        """
    )
    fun getPlan(beginDate: String, endDate: String): List<ReturnPlanName>

    @Insert
    fun addPlan(plan: Plan)
}
