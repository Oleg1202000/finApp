package com.oleg1202000.finapp.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.oleg1202000.finapp.data.Categories

interface CategoriesDao {
    @Query(
        """
        SELECT * FROM categories
        """
    )
    fun getCategories(): List<Categories>


    @Insert
    fun addCategory(сategory: Categories)


    @Delete
    fun deleteCategory(сategory: Categories)
}