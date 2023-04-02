package com.oleg1202000.finapp.data

import androidx.room.TypeConverter
import java.sql.Date

class DateConverter {
    @TypeConverter
    fun longToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToLong(value: Date?): Long? {
        return value?.time?.toLong()
    }
}