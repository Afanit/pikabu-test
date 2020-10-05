package ru.aphanite.pikabu_test.ui.story.item

import android.graphics.drawable.Drawable
import android.util.Size
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.aphanite.pikabu_test.R
import ru.aphanite.pikabu_test.databinding.StoryImageBinding
import ru.aphanite.pikabu_test.utils.AnimationUtils

class StoryImageViewHolder(
    private val binding: StoryImageBinding,
    private val imageSize: Size
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(url: String) {
        Glide.with(binding.root)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade(AnimationUtils.LONG_DURATION.toInt()))
            .placeholder(R.drawable.image_placeholder)
            .error(R.drawable.image_error)
            // Glide bug with placeholder, otherwise image will be smaller
            .override(imageSize.width, imageSize.height)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.root)
    }
}