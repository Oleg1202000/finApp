package com.oleg1202000.finapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "targets",
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Targets(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: ULong,
    @ColumnInfo(name = "user_id") val userId: ULong,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "amount") val amount: UInt
    // add date
)