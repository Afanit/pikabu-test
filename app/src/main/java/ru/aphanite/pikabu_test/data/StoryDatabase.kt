package ru.aphanite.pikabu_test.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [StoryEntity::class], version = 1)
@TypeConverters(StoryConverters::class)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun dao(): StoryDao
}