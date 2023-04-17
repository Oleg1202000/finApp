package com.oleg1202000.finapp.data

import androidx.room.*
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
       /* ForeignKey(
            entity = Tags::class,
            parentColumns = ["id"],
            childColumns = ["tag_id"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        )*/
    ],
    indices = [
        Index("category_id", unique = false),
        //Index("tag_id", unique = false)
    ]
)
data class Summary(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "category_id") val categoryId: Long,
    //@ColumnInfo(name = "tag_id", defaultValue = "NULL") val tagId: Long?,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "date") val date: Date?,
    @ColumnInfo(name = "is_sync") val isSync: Boolean,
    @ColumnInfo(name = "about", defaultValue = "NULL") val about: String?
)

data class ReturnSumAmount(

    @ColumnInfo(name = "category_id") val categoryId: Long,
    @ColumnInfo(name = "amount") val amount: Int
)

data class ReturnSumAmountAndPlan(

    @ColumnInfo(name = "category_name") val categoryName: String,
    @ColumnInfo(name = "summary_amount") val amount: Int,
    @ColumnInfo(name = "planned") val plan: Int?
)


data class ReturnHistory(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name") val categoryName: String,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "date") val date: Date?,
    @ColumnInfo(name = "about") val about: String?
)
