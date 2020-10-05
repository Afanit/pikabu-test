package ru.aphanite.pikabu_test.ui.story.item

import android.util.Size
import androidx.recyclerview.widget.RecyclerView
import ru.aphanite.pikabu_test.databinding.StoryItemBinding
import ru.aphanite.pikabu_test.model.StoryModel
import ru.aphanite.pikabu_test.ui.story.listener.OnFavoriteClickListener
import ru.aphanite.pikabu_test.ui.story.listener.OnStoryClickListener
import ru.aphanite.pikabu_test.utils.ColorConst

class StoryViewHolder(
    private val binding: StoryItemBinding,
    colors: ColorConst,
    displaySize: Size
) :
    RecyclerView.ViewHolder(binding.root) {

    private val binder = ItemStoryBinder(binding, colors, displaySize)

    fun bind(
        data: StoryModel,
        storyListener: OnStoryClickListener,
        favoriteListener: OnFavoriteClickListener
    ) {
        binder.bind(data)
        binder.title.setOnClickListener {
            storyListener.onAction(data, binding.root, adapterPosition)
        }
        binder.favoriteListener = favoriteListener
    }

    private inner class ItemStoryBinder(
        binding: StoryItemBinding,
        colors: ColorConst,
        displaySize: Size
    ) : StoryBinder(colors, displaySize) {

        override val title = binding.title
        override val body = binding.body
        override val images = binding.images
        override val mark = binding.mark
        override val favoriteButton = binding.favoriteButton
    }
}
