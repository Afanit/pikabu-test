package ru.aphanite.pikabu_test.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.aphanite.pikabu_test.database.entity.StoryEntity
import ru.aphanite.pikabu_test.database.entity.StoryEntityFavorite
import ru.aphanite.pikabu_test.database.entity.StoryEntityServer

@Dao
interface StoryDao {
    @Query("SELECT * FROM story_entity ORDER BY id DESC")
    fun getAllStory(): LiveData<List<StoryEntity>>

    @Query("SELECT * FROM story_entity WHERE id IS :id")
    fun findStoryById(id: Long): LiveData<StoryEntity>

    @Query("SELECT * FROM story_entity WHERE id IS :id")
    fun loadStoryById(id: Long): StoryEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllStory(stories: List<StoryEntity>): List<Long>

    @Update(entity = StoryEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun updateAllStory(stories: List<StoryEntityServer>)

    @Update(entity = StoryEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun updateStory(story: StoryEntityServer)

    @Update(entity = StoryEntity::class)
    fun updateFavorite(story: StoryEntityFavorite)

    @Query("SELECT * FROM story_entity WHERE isFavorite = 1 ORDER BY favoriteTimestamp DESC")
    fun getAllSortedFavoriteStory(): LiveData<List<StoryEntity>>
}