package com.oleg1202000.finapp.data.database

import androidx.room.TypeConverter
import java.util.Date


class DateConverter {
    @TypeConverter
    fun longToDate(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToLong(value: Date): Long {
        return value.time
    }
}