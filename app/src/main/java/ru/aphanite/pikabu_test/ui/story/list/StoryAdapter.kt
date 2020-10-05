package ru.aphanite.pikabu_test.ui.story.list

import android.util.Size
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.aphanite.pikabu_test.model.StoryModel
import ru.aphanite.pikabu_test.databinding.StoryItemBinding
import ru.aphanite.pikabu_test.ui.story.item.StoryViewHolder
import ru.aphanite.pikabu_test.ui.story.listener.OnFavoriteClickListener
import ru.aphanite.pikabu_test.ui.story.listener.OnStoryClickListener
import ru.aphanite.pikabu_test.utils.ColorConst
import javax.inject.Inject

class StoryAdapter @Inject constructor(
    private val displaySize: Size,
    private val favoriteListener: OnFavoriteClickListener,
    private val storyListener: OnStoryClickListener,
    private val colors: ColorConst
) : RecyclerView.Adapter<StoryViewHolder>() {
    private var stories: List<StoryModel> = listOf()

    fun setStories(data: List<StoryModel>) {
        DiffUtil.calculateDiff(StoryDiffCallback(stories, data)).dispatchUpdatesTo(this)

        stories = data
    }

    fun forceSetStories(data: List<StoryModel>) {
        DiffUtil.calculateDiff(ForceStoryDiffCallback(stories, data)).dispatchUpdatesTo(this)

        stories = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return StoryViewHolder(binding, colors, displaySize)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(stories[position], storyListener, favoriteListener)
    }

    override fun getItemCount(): Int {
        return stories.size
    }
}