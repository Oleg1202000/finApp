package com.oleg1202000.finapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import java.sql.Date


@Entity (
    tableName = "plan",
    foreignKeys = [
        ForeignKey(
            entity = Categories::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Plan(
    @ColumnInfo (name = "cateory_id") val categoryId: ULong,
    @ColumnInfo (name = "limit") val limit: UInt,
    @ColumnInfo (name = "date") val date: Date
)

data class ReturnPlanName(
    val categoryName: String,
    val limit: UInt
)
