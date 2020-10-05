package ru.aphanite.pikabu_test.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.aphanite.pikabu_test.database.entity.StoryEntity


@Database(entities = [StoryEntity::class], version = 1)
@TypeConverters(StoryConverters::class)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun dao(): StoryDao
}