package ru.aphanite.pikabu_test.utils

import ru.aphanite.pikabu_test.database.entity.StoryEntity
import ru.aphanite.pikabu_test.model.StoryModel

object ModelMapper {

    fun mapEntitiesToModels(entities: List<StoryEntity>): List<StoryModel> {
        return entities.map { entity ->
            StoryModel(entity.id, entity.title, entity.body, entity.urls, entity.isFavorite)
        }
    }

    fun mapEntityToModel(entity: StoryEntity): StoryModel {
        return StoryModel(entity.id, entity.title, entity.body, entity.urls, entity.isFavorite)
    }
}