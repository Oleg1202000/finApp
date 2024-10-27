package com.mk1morebugs.finapp.data.local.room.dao

import androidx.room.*
import com.mk1morebugs.finapp.data.local.room.CostForUi
import com.mk1morebugs.finapp.data.local.room.CostHistory
import com.mk1morebugs.finapp.data.local.room.Cost
import kotlinx.coroutines.flow.Flow

@Dao
interface CostsDao {
    @Query(
        """
        SELECT 
            categories.name AS category_name,
            categories.color AS icon_color,
            categories.icon_id AS icon_id, 
            SUM(costs.amount) AS summary_amount
        FROM costs
        JOIN categories ON categories.id = costs.category_id
        
        WHERE costs.date >= :beginDate AND costs.date <= :endDate 
            AND categories.is_income = :isIncome
            AND costs.is_planned = :isPlanned
        GROUP BY categories.id
        ORDER BY summary_amount DESC
        """
    )
    fun getCostsForUi(
        isIncome: Boolean,
        isPlanned: Boolean,
        beginDate: Long,
        endDate: Long,
    ): Flow<List<CostForUi>>

    @Query(
        """
        SELECT 
            costs.id, 
            categories.name,
            categories.is_income,
            categories.icon_id, 
            categories.color AS icon_color, 
            costs.amount, 
            costs.date, 
            costs.about
        FROM costs
        INNER JOIN categories ON categories.id = costs.category_id
        WHERE costs.date >= :beginDate AND costs.date <= :endDate
            AND costs.is_planned = :isPlanned
        ORDER BY costs.date DESC
        """
    )
    fun getCostsHistory(
        beginDate: Long,
        endDate: Long,
        isPlanned: Boolean,
    ): Flow<List<CostHistory>>

    @Insert
     suspend fun setCost(cost: Cost)

    @Query(
        """
        DELETE FROM costs WHERE id = :id
        """
    )
    suspend fun deleteCostById(id: Long)

    @Update
    suspend  fun updateCost(cost: Cost)
}