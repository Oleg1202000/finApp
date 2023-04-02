package com.oleg1202000.finapp.data.dao

import androidx.room.*
import com.oleg1202000.finapp.data.*
import java.sql.Date

@Dao
interface SummaryDao {
    @Query(
        """
        SELECT summary.category_id, SUM(summary.amount) AS amount
        
        FROM summary
        
        INNER JOIN summary_tags ON summary_tags.summary_id = summary.id
        INNER JOIN tags ON summary_tags.tag_id = tags.id
        INNER JOIN categories ON categories.id = summary.category_id
        
        WHERE categories.id IN (:categoryIds)
        AND tags.id IN (:tagsIds)
        AND summary.date >= :beginDate AND summary.date <= :endDate
        
        GROUP BY summary.category_id
        """
    )
    fun getSumAmount(
        categoryIds: List<Long>,
        tagsIds: List<Long>,
        beginDate: Date?,
        endDate: Date?
    ): List<ReturnSumAmount>


    @Query(
        """
        SELECT summary.id, categories.name, summary.amount, summary.date, summary.about
        
        FROM summary
        
        INNER JOIN summary_tags ON summary_tags.summary_id = summary.id
        INNER JOIN tags ON summary_tags.tag_id = tags.id
        INNER JOIN categories ON categories.id = summary.category_id
        
        WHERE summary.category_id IN (:categoryIds) AND
        summary_tags.tag_id IN (:tagIds) AND
        summary.date >= :beginDate AND summary.date <= :endDate
        
        ORDER BY summary.date DESC
        """
    )
    fun getHistory(
        tagIds: List<Long>,
        categoryIds: List<Long>,
        endDate: Date?,
        beginDate: Date?
    ): List<ReturnHistory>


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
