package com.oleg1202000.finapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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

    ) : List<Category>

    // TODO: создать (или изменить) запрос, где не будет возвращаться is_income


    @Insert
    suspend  fun setCategory(category: Category): Long



    @Delete
    suspend  fun deleteCategory(category: Category)
}