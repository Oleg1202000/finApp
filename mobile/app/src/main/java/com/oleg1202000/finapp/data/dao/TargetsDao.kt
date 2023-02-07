package com.oleg1202000.finapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.oleg1202000.finapp.data.Targets
import com.oleg1202000.finapp.data.Users

@Dao
interface TargetsDao {
    @Insert
    fun addTarget(target: Targets)

    @Delete
    fun deleteTarget(id: Long)

    @Query("SELECT * FROM targets")
    fun getAllTargets(): List<Targets>
}
