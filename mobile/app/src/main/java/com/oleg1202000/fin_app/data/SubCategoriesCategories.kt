package com.oleg1202000.fin_app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "subcategories_categories",
    foreignKeys = [
        ForeignKey(
            entity = SubCategories::class,
            parentColumns = ["id"],
            childColumns = ["subcategory_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Categories::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class SubCategoriesCategories(
    @ColumnInfo(name = "subcategory_id") val subCategoryId: Long,
    @ColumnInfo(name = "category_id") val categoryId: Long,
)