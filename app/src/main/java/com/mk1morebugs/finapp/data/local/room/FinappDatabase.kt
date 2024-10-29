package com.mk1morebugs.finapp.data.local.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteTable
import androidx.room.RenameColumn
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.mk1morebugs.finapp.data.local.room.dao.CategoriesDao
import com.mk1morebugs.finapp.data.local.room.dao.CostsDao

@Database (
    entities = [
        Category::class,
        Cost::class,
    ],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(
            from = 1, to = 2,
            spec = FinappDatabase.MigrationVersion1ToVersion2::class
        ),

    ]
)
abstract class FinappDatabase : RoomDatabase() {

    abstract fun categoriesDao(): CategoriesDao

    abstract fun costsDao(): CostsDao

    @RenameTable(fromTableName = "summary", toTableName = "costs")
    @RenameColumn(tableName = "summary", fromColumnName = "is_sync", toColumnName = "is_planned")
    @DeleteTable(tableName = "planned")
    class MigrationVersion1ToVersion2 : AutoMigrationSpec
}
