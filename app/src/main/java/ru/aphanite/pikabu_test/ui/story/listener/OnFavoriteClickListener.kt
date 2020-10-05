package ru.aphanite.pikabu_test.ui.story.listener

import ru.aphanite.pikabu_test.model.StoryModel

fun interface OnFavoriteClickListener {
    fun onAction(data: StoryModel)
}