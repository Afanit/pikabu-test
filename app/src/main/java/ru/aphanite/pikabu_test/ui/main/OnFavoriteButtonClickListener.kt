package ru.aphanite.pikabu_test.ui.main

import ru.aphanite.pikabu_test.data.StoryModel

fun interface OnFavoriteButtonClickListener {
    fun onAction(data: StoryModel)
}