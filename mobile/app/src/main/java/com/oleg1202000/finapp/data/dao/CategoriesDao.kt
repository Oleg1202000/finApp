package com.oleg1202000.finapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.oleg1202000.finapp.data.Category
import com.oleg1202000.finapp.data.CategoryWithoutIsIncome
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {
    @Query(
        """
        SELECT  id, name, color, path_to_icon FROM categories WHERE is_income = :isIncome
        """
    )
    fun getCategories(
        isIncome: Boolean = false

    ) : Flow<List<CategoryWithoutIsIncome>>


    @Insert
    suspend  fun setCategory(category: Category)


    @Delete
    suspend  fun deleteCategory(category: Category)
}