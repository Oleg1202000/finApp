package com.oleg1202000.finapp.data.dao

import androidx.room.*
import com.oleg1202000.finapp.data.Users


@Dao
interface UsersDao {
    @Query("SELECT name FROM users")
    fun getAllUsers(): List<String>

    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUser(userId: Long): Users

    @Insert
    fun addUser(user: Users)

    @Delete
    fun deleteUser(user: Users)
    @Update
    fun updateUser(user: Users)
}