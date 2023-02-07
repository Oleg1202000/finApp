package com.oleg1202000.finapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.oleg1202000.finapp.data.Users


@Dao
interface UsersDao {
    @Query("SELECT name FROM users")
    fun getAllUsers(): List<Users>

    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUser(userId: Long): Users

    @Insert
    fun addtUser(name: Users)

    @Delete
    fun deleteUser(id: Users)
}