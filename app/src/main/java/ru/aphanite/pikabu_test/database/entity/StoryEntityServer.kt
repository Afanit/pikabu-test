package ru.aphanite.pikabu_test.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StoryEntityServer(
    @PrimaryKey val id: Long,
    val title: String,
    val body: String?,
    val urls: List<String>?
)