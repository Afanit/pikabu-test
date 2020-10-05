package ru.aphanite.pikabu_test.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story_entity")
data class StoryEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val body: String?,
    val urls: List<String>?,
    val isFavorite: Boolean = false,
    val favoriteTimestamp: Long = 0
)