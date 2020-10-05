package ru.aphanite.pikabu_test.ui.story.list

import androidx.recyclerview.widget.DiffUtil
import ru.aphanite.pikabu_test.model.StoryModel

class ForceStoryDiffCallback(
    private val oldList: List<StoryModel>,
    private val newList: List<StoryModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old == new && old.isFavorite == new.isFavorite
    }
}