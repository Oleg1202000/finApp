package com.oleg1202000.finapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.oleg1202000.finapp.data.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {
    @Query(
        """
        SELECT * FROM categories WHERE is_income = :isIncome
        """
    )
    fun getCategories(
        isIncome: Boolean = false

    ) : Flow<List<Category>>


    @Insert
    suspend  fun setCategory(category: Category)


    @Delete
    suspend  fun deleteCategory(category: Category)
}