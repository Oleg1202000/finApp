package com.oleg1202000.finapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey


@Entity(
    tableName = "summary_tags",
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
data class SummaryTags(
    @ColumnInfo(name = "summary_id") val summaryId: Long,
    @ColumnInfo(name = "tag_id") val tegId: Long,
)