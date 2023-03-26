package com.oleg1202000.finapp.data.dao

import androidx.room.*
import com.oleg1202000.finapp.data.*

@Dao
interface SummaryDao {
    @Query(
        """
        SELECT summary.category_id, SUM(summary.amount)
        
        FROM summary
        
         JOIN summary_tags ON summary_tags.summary_id = summary.id
        RIGHT JOIN tags ON summary.tag_id = tags.id
        INNER JOIN categories ON categories.id = summary.category_id
        
        WHERE categories.id IN (:categoryIds) AND
        tags.id IN (:tagsIds) AND
        summary.date >= :beginDate AND summary.date <= :endDate
        
        GROUP BY summary.category_id
        """
    )
    fun getSumAmount(
        categoryIds: List<ULong>,
        tagsIds: List<ULong>,
        beginDate: String,
        endDate: String
    ): List<ReturnSumAmount>


    @Query(
        """
        SELECT summary.id, categories.name, tags.name, summary.amount, summary.date, summary.about
        
        FROM summary
        
        RIGHT JOIN tags ON tags.id = summary.tag_id
        INNER JOIN categories ON summary.category_id = categories.id
        
        WHERE summary.category_id IN (:categoryIds) AND
        summary.tag_id IN (:tagIds) AND
        summary.date >= :beginDate AND summary.date <= :endDate
        
        ORDER BY summary.date DESC
        """
    )
    fun getHistory(
        tagIds: List<ULong>,
        categoryIds: List<ULong>,
        endDate: String,
        beginDate: String
    ): List<ReturnHistory>


    @Insert
    fun addSummary(summary: Summary)


    //delete
    @Query(
        """
        DELETE FROM summary
        WHERE id = :id
        """
    )
    fun deleteSummary(id: ULong)


    @Update
    fun updateSummary(summary: Summary)
}
