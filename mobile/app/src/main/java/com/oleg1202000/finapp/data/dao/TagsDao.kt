package com.oleg1202000.finapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.oleg1202000.finapp.data.Tags

@Dao
interface TagsDao {
    @Query(
        """
        SELECT * FROM tags
        """
    )
    fun getTags(): List<Tags>


    // TODO: Реализовать SELECT по summary_id в summary_tags


    @Insert
    fun setTag(tag: Tags)


    @Delete
    fun deleteTag(tag: Tags)
}