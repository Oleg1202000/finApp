package com.oleg1202000.finapp.data.dao

import androidx.room.*
import com.oleg1202000.finapp.data.*

@Dao
interface SummaryDao {
    @Query(
        """
        SELECT summary.subcategory_id, SUM(summary.amount)
        
        FROM summary
        
        RIGHT JOIN summary_tags ON summary_tags.summary_id = summary.id
        INNER JOIN tags ON summary_tags.tag_id = tags.id
        INNER JOIN subcategories_categories ON subcategories_categories.subcategory_id = summary.subcategory_id
        
        WHERE subcategories_categories.category_id IN (:CategoryIds) AND
        summary.date >= :BeginDate AND summary.date <= :EndDate AND user_id = :UserId
        
        GROUP BY subcategory_id
            """
    )
    fun getSumAmount(
        UserId: ULong,
        CategoryIds: List<ULong>,
        EndDate: String,
        BeginDate: String
    ): List<ReturnSumAmount>


    @Query("""
        SELECT summary.id, tags.name, summary.amount, summary.date, summary.about
        
        FROM summary
        
        RIGHT JOIN summary_tags ON summary_tags.summary_id = summary.id
        INNER JOIN accounts ON summary.account_id = accounts.id
        
        WHERE accounts.user_id IN (:UserIds) AND
        summary.subcategory_id IN (:SubCategoryIds) AND
        summary_tags.tag_id IN (:TagIds) AND
        summary.account_id IN (:AccountIds) AND
        summary.date >= :BeginDate AND summary.date <= :EndDate AND user_id = :UserId
        
        ORDER BY summary.date DESC, summary.time DESC
            """
    )
    fun getHistory(
        UserIds: List<ULong>,
        TagIds: List<ULong>,
        AccountIds: List<ULong>,
        SubCategoryIds: List<ULong>,
        EndDate: String,
        BeginDate: String
    ): List<ReturnHistory>


    @Query("""
        SELECT tags.name, accounts.name, subcategories.name, summary.amount, summary.date, summary.time, summary.about
        
        FROM summary
        
        RIGHT JOIN summary_tags ON summary_tags.summary_id = summary.id
        INNER JOIN tags ON summary_tags.tag_id = tags.id
        INNER JOIN subcategories ON subcategories.id = summary.subcategory_id
        INNER JOIN accounts ON summary.account_id = accounts.id
        INNER JOIN users ON users.id = accounts.user_id
        
        WHERE users.id = :UserId
        
        ORDER BY summary.date DESC, summary.time DESC
            """
    )
    fun getHistoryOne(
        UserId: ULong
    ): List<ReturnHistoryOne>


    @Insert
    fun addSummary(summary: Summary)

    @Delete
    fun deleteSummary(summary: Summary)

    @Update
    fun updateSummary(summary: Summary)
}
