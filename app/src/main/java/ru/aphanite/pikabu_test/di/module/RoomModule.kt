package ru.aphanite.pikabu_test.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.aphanite.pikabu_test.data.StoryDao
import ru.aphanite.pikabu_test.data.StoryDatabase
import ru.aphanite.pikabu_test.di.qualifier.AppContext
import ru.aphanite.pikabu_test.di.scope.AppScope

@Module
class RoomModule {

    @AppScope
    @Provides
    fun provideStoryDatabase(@AppContext applicationContext: Context): StoryDatabase {
        return Room.databaseBuilder(applicationContext, StoryDatabase::class.java, "story-database").build()
    }

    @AppScope
    @Provides
    fun provideStoryDao(database: StoryDatabase): StoryDao {
        return database.dao()
    }
}