package com.oleg1202000.finapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.oleg1202000.finapp.data.Categories

@Dao
interface CategoriesDao {
    @Query(
        """
        SELECT * FROM categories WHERE is_income = :isIncome
        """
    )
    fun getCategories(
        isIncome: Boolean = false

    ) : List<Categories>


    @Update
    fun setCategory(category: Categories)


    @Delete
    fun deleteCategory(category: Categories)
}