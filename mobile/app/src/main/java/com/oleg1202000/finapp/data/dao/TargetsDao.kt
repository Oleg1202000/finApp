package com.oleg1202000.finapp.data.dao

import androidx.room.*
import com.oleg1202000.finapp.data.Targets

@Dao
interface TargetsDao {
    @Insert
    fun addTarget(target: Targets)

    @Delete
    fun deleteTarget(target: Targets)

    @Update
    fun updateTarget(target: Targets)

    @Query("SELECT * FROM targets")
    fun getAllTargets(): List<Targets>
}
