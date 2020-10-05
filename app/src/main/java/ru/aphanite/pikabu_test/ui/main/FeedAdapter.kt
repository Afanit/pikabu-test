package ru.aphanite.pikabu_test.ui.main

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.os.Handler
import android.os.Looper
import android.util.Size
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.aphanite.pikabu_test.R
import ru.aphanite.pikabu_test.data.StoryEntityFavorite
import ru.aphanite.pikabu_test.data.StoryModel
import ru.aphanite.pikabu_test.databinding.StoryImageBinding
import ru.aphanite.pikabu_test.databinding.StoryItemBinding
import ru.aphanite.pikabu_test.di.scope.FragmentScope
import javax.inject.Inject

@FragmentScope
class FeedAdapter @Inject constructor(
    private val displaySize: Size,
    private val favoriteListener: FeedOnFavoriteClickListener
) : RecyclerView.Adapter<StoryViewHolder>() {
    private var stories: List<StoryModel> = listOf()

    fun setStories(data: List<StoryModel>) {
        DiffUtil.calculateDiff(FeedDiffCallback(stories, data)).dispatchUpdatesTo(this)

        stories = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val bindings = StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(bindings, displaySize)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(stories[position], favoriteListener)
    }

    override fun getItemCount(): Int {
        return stories.size
    }
}

class StoryViewHolder(private val binding: StoryItemBinding, displaySize: Size) :
    RecyclerView.ViewHolder(binding.root) {

    private val adapter = StoryImageAdapter(displaySize)
    private lateinit var favoriteListener: OnFavoriteButtonClickListener

    fun bind(data: StoryModel, favoriteListener: OnFavoriteButtonClickListener) {
        this.favoriteListener = favoriteListener

        with(binding) {
            title.text = data.title

            if (data.body.isNullOrBlank()) {
                body.isVisible = false
            } else {
                body.isVisible = true
                body.text = data.body
            }

            mark.isVisible = data.isFavorite

            actionButton.isSelected = data.isFavorite
            actionButton.setOnClickListener {
                data.isFavorite  = !data.isFavorite
                favoriteListener.onAction(data)

                if (data.isFavorite) {
                    ValueAnimator.ofFloat(0f, 1f).apply {
                        duration = 300
                        doOnStart {
                            mark.isVisible = true
                        }
                        addUpdateListener {
                            binding.mark.alpha = (it.animatedValue as Float)
                        }
                        start()
                    }
                    val start = ResourcesCompat.getColor(root.resources, R.color.colorDark, null)
                    val end = ResourcesCompat.getColor(root.resources, R.color.colorOrange, null)

                    ValueAnimator.ofObject(ArgbEvaluator(), start, end).apply {
                        duration = 300
                        addUpdateListener {
                            binding.actionButton.setColorFilter(it.animatedValue as Int)
                        }
                        doOnEnd {
                            binding.actionButton.isSelected = true
                        }
                        start()
                    }
                }
                else {
                    ValueAnimator.ofFloat(1f, 0f).apply {
                        duration = 300
                        addUpdateListener {
                            binding.mark.alpha = (it.animatedValue as Float)
                        }
                        doOnEnd {
                            mark.isVisible = false
                        }
                        start()
                    }
                    val start = ResourcesCompat.getColor(root.resources, R.color.colorOrange, null)
                    val end = ResourcesCompat.getColor(root.resources, R.color.colorWhite, null)
                    val dark = ResourcesCompat.getColor(root.resources, R.color.colorDark, null)

                    ValueAnimator.ofObject(ArgbEvaluator(), start, end).apply {
                        duration = 300
                        addUpdateListener {
                            binding.actionButton.setColorFilter(it.animatedValue as Int)
                        }
                        doOnEnd {
                            binding.actionButton.isSelected = false
                            binding.actionButton.setColorFilter(dark)
                        }
                        start()
                    }
                }
            }

            images.adapter = adapter
            adapter.setUrls(data.urls)
        }
    }
}

class StoryImageAdapter(private val imageSize: Size) :
    RecyclerView.Adapter<StoryImageViewHolder>() {
    private var urls: List<String> = emptyList()

    fun setUrls(data: List<String>?) {
        urls = data ?: emptyList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryImageViewHolder {
        val bindings = StoryImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryImageViewHolder(bindings, imageSize)
    }

    override fun onBindViewHolder(holder: StoryImageViewHolder, position: Int) {
        holder.bind(urls[position])
    }

    override fun getItemCount(): Int {
        return urls.size
    }

}

class StoryImageViewHolder(private val bindings: StoryImageBinding, private val imageSize: Size) :
    RecyclerView.ViewHolder(bindings.root) {

    fun bind(url: String) {
        Glide.with(bindings.root)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.drawable.image_placeholder)
            .error(R.drawable.image_error)
            .override(imageSize.width, imageSize.height)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter()
            .into(bindings.root)
    }
}