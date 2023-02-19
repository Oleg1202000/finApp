package com.oleg1202000.finapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories"
)
data class Categories(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: ULong,
    @ColumnInfo(name = "name") val name: String

    // добовить уникальные индексы в справочники


)