package com.mk1morebugs.finapp.data.local.room

import androidx.room.*

@Entity(
    tableName = "costs",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("category_id", unique = false),
    ]
)
data class Cost(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "category_id") val categoryId: Long,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "is_planned") val isPlanned: Boolean = false,
    @ColumnInfo(name = "about", defaultValue = "NULL") val about: String?
)

data class CostForUi(
    @ColumnInfo(name = "category_name") val categoryName: String,
    @ColumnInfo(name = "icon_color") val iconColor: Long,
    @ColumnInfo(name = "icon_id") val iconId: Int,
    @ColumnInfo(name = "summary_amount") val summaryAmount: Int
)

data class CostHistory(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name") val categoryName: String,
    @ColumnInfo(name = "is_income") val isIncome: Boolean,
    @ColumnInfo(name = "icon_id") val iconId: Int,
    @ColumnInfo(name = "icon_color") val iconColor: Long,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "about") val about: String?
)
