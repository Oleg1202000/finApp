package com.mk1morebugs.finapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mk1morebugs.finapp.data.database.Category
import com.mk1morebugs.finapp.data.database.CategoryWithoutIsIncome
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {
    @Query(
        """
        SELECT  id, name, color, icon_id FROM categories WHERE is_income = :isIncome
        """
    )
    fun getCategories(
        isIncome: Boolean = false

    ) : Flow<List<CategoryWithoutIsIncome>>


    @Insert
    suspend  fun setCategory(category: Category)


    //delete
    @Query(
        """
        DELETE FROM categories
        WHERE id = :id
        """
    )
    suspend fun deleteCategoryById(id: Long)
}