package com.oleg1202000.finapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Date
import java.sql.Time

@Entity(
    tableName = "summary",
    foreignKeys = [
        ForeignKey(
            entity = Accounts::class,
            parentColumns = ["id"],
            childColumns = ["account_id"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SubCategories::class,
            parentColumns = ["id"],
            childColumns = ["subcategory_id"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Summary(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: ULong,
    @ColumnInfo(name = "account_id") val accountId: ULong,
    @ColumnInfo(name = "subcategory_id") val subCategoryId: ULong,
    @ColumnInfo(name = "amount") val amount: UInt,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "time") val time: Time,
    @ColumnInfo(name = "is_sync") val isSync: Boolean,
    @ColumnInfo(name = "about", defaultValue = "NULL") val about: String?
)

data class ReturnSumAmount(
    val subCategoryId: ULong,
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
    val subCategoryName: String,
    val amount: UInt,
    val date: Date,
    val time: Time,
    val about: String?
)