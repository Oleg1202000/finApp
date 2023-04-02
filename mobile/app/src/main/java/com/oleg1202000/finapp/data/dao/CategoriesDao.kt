package com.oleg1202000.finapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.oleg1202000.finapp.data.Categories

@Dao
interface CategoriesDao {
    @Query(
        """
        SELECT * FROM categories
        """
    )
    fun getCategories(): List<Categories>


    @Insert
    fun setCategory(category: Categories)


    @Delete
    fun deleteCategory(category: Categories)
}