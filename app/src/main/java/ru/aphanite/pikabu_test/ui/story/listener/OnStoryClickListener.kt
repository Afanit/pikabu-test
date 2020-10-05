package ru.aphanite.pikabu_test.ui.story.listener

import androidx.constraintlayout.widget.ConstraintLayout
import ru.aphanite.pikabu_test.model.StoryModel

fun interface OnStoryClickListener {
    fun onAction(data: StoryModel, view: ConstraintLayout, adapterPosition: Int)
}