package ru.aphanite.pikabu_test.database

import androidx.lifecycle.LiveData
import ru.aphanite.pikabu_test.database.entity.StoryEntity
import ru.aphanite.pikabu_test.database.entity.StoryEntityFavorite
import ru.aphanite.pikabu_test.database.entity.StoryEntityServer
import ru.aphanite.pikabu_test.di.scope.AppScope
import ru.aphanite.pikabu_test.model.StoryModel
import ru.aphanite.pikabu_test.network.StoryDto
import javax.inject.Inject

@AppScope
class StoryRepository @Inject constructor(private val storyDao: StoryDao) {
    fun getAllStory(): LiveData<List<StoryEntity>> {
        return storyDao.getAllStory()
    }

    fun findStoryById(id: Long): LiveData<StoryEntity> {
        return storyDao.findStoryById(id)
    }

    fun saveAllStory(stories: List<StoryDto>) {
        val entities = stories.map { StoryEntity(it.id, it.title, it.body, it.urls) }
        val conflicts = stories.zip(storyDao.insertAllStory(entities))
            .asSequence()
            .filter { it.second == -1L }
            .map {
                val story = it.first
                StoryEntityServer(story.id, story.title, story.body, story.urls)
            }.toList()

        storyDao.updateAllStory(conflicts)
    }

    fun updateFavorite(story: StoryModel) {
        val timestamp = if (story.isFavorite) System.nanoTime() else 0

        storyDao.updateFavorite(
            StoryEntityFavorite(
                story.id,
                story.isFavorite,
                timestamp
            )
        )
    }

    fun updateStory(story: StoryDto) {
        val newEntity = StoryEntityServer(story.id, story.title, story.body, story.urls)

        val old = storyDao.loadStoryById(story.id)
        val oldEntity = StoryEntityServer(old.id, old.title, old.body, old.urls)

        if (newEntity != oldEntity) {
            storyDao.updateStory(newEntity)
        }
    }

    fun getAllFavoriteStory(): LiveData<List<StoryEntity>> {
        return storyDao.getAllSortedFavoriteStory()
    }
}