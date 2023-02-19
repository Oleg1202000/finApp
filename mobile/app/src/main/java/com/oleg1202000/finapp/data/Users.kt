package com.oleg1202000.finapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "users"
)
data class Users(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: ULong,
    @ColumnInfo(name = "name") val name: String
)

