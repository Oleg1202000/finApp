package com.oleg1202000.finapp.data

import androidx.room.*


@Entity(
    tableName = "summary_tags" ,
    indices = [
        Index("summary_id", unique = false),
        Index("tag_id", unique = false),
              ],
    foreignKeys = [
        ForeignKey(
                entity = Summary::class,
                parentColumns = ["id"],
                childColumns = ["summary_id"],
                onDelete = ForeignKey.CASCADE,
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
data class SummaryTags (
    @PrimaryKey(autoGenerate = true)  @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "summary_id") val categoryId: Long,
    @ColumnInfo(name = "tag_id") val tagId: Long,
)