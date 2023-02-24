package com.oleg1202000.finapp.data

import android.content.Context
import androidx.room.Room


class DatabaseBuilder {
    fun bilder(context: Context) {
        val db = Room.databaseBuilder(
            context,
            FinappDatabase::class.java, "finappDatabase"
        ).build()
    }
}