package com.oleg1202000.finapp.data

import androidx.room.*
import java.util.Date


@Entity (
    tableName = "planned",

    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        )
    ],

    indices = [
        Index("category_id", unique = false)
    ]
)
data class Planned(
    @PrimaryKey (autoGenerate = true)  @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo (name = "category_id") val categoryId: Long,
    @ColumnInfo (name = "amount") val amount: Int,
    @ColumnInfo (name = "date") val date: Date
)
