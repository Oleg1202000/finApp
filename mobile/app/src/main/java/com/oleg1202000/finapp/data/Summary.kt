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
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "subcategory_id") val subCategoryId: Long,
    @ColumnInfo(name = "amount") val amount: UInt,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "time") val time: Time,
    @ColumnInfo(name = "is_income") val isIncome: Boolean,
    @ColumnInfo(name = "about", defaultValue = "NULL") val about: String?
)

data class ReturnSumAmount(
    val subCategoryId: Long,
    val sumAmount: UInt
)