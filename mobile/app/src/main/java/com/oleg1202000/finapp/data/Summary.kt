package com.oleg1202000.finapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(
    tableName = "summary",
    foreignKeys = [
        ForeignKey(
            entity = Categories::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Tags::class,
            parentColumns = ["id"],
            childColumns = ["tag_id"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Summary(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: ULong,
    @ColumnInfo(name = "category_id") val categoryId: ULong,
    @ColumnInfo(name = "tag_id", defaultValue = "NULL") val tegId: ULong?,
    @ColumnInfo(name = "amount") val amount: UInt,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "is_sync") val isSync: Boolean,
    @ColumnInfo(name = "about", defaultValue = "NULL") val about: String?
)

data class ReturnSumAmount(
    val categoryId: ULong,
    val sumAmount: UInt
)


data class ReturnHistory(
    val id: ULong,
    val tagName: String,
    val amount: UInt,
    val date: Date,
    val about: String?
)


data class ReturnHistoryOne(
    val tagName: String,
    val accountName: String,
    val categoryName: String,
    val amount: UInt,
    val date: Date,
    val about: String?
)