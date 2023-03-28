package com.oleg1202000.finapp.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.oleg1202000.finapp.data.Categories
import com.oleg1202000.finapp.data.Tags

interface TagsDao {
    @Query(
        """
        SELECT * FROM tags
        """
    )
    fun getTags(): List<Categories>


    @Insert
    fun addTag(tag: Tags)


    @Delete
    fun deleteTag(tag: Tags)
}