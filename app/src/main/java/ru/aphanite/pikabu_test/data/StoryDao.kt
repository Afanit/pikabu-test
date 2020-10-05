package ru.aphanite.pikabu_test.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface StoryDao {
    @Query("SELECT * FROM story_entity")
    fun getAllStory(): LiveData<List<StoryEntity>>

    @Query("SELECT * FROM story_entity WHERE id IS :id")
    fun findStoryById(id: Long): LiveData<StoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStory(items: List<StoryEntity>)

    @Update(entity = StoryEntity::class)
    fun updateFavorite(story: StoryEntityFavorite)
}

data class StoryEntityFavorite(val id: Long, val isFavorite: Boolean)