package ru.aphanite.pikabu_test.ui.story.item

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.util.Size
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.aphanite.pikabu_test.model.StoryModel
import ru.aphanite.pikabu_test.ui.story.listener.OnFavoriteClickListener
import ru.aphanite.pikabu_test.utils.AnimationUtils
import ru.aphanite.pikabu_test.utils.ColorConst

abstract class StoryBinder(private val colors: ColorConst, displaySize: Size) {
    private val adapter = StoryImageAdapter(displaySize)

    abstract val title: TextView
    abstract val body: TextView
    abstract val images: RecyclerView
    abstract val mark: View
    abstract val favoriteButton: ImageButton

    var favoriteListener: OnFavoriteClickListener? = null

    fun bind(data: StoryModel) {
        title.text = data.title

        if (data.body.isNullOrBlank()) {
            body.isVisible = false
            body.text = null
        } else {
            body.isVisible = true
            body.text = data.body
        }

        mark.isVisible = data.isFavorite
        mark.alpha = 1f

        favoriteButton.isSelected = data.isFavorite
        favoriteButton.setColorFilter(if (data.isFavorite) colors.orange else colors.dark)

        favoriteButton.setOnClickListener {
            data.isFavorite = !data.isFavorite
            if (data.isFavorite) {
                markAsFavorite()
            } else {
                unmarkAsFavorite()
            }
            favoriteListener?.onAction(data)
        }

        images.adapter = adapter.apply {
            setUrls(data.urls)
        }
    }

    private fun markAsFavorite() {
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = AnimationUtils.SHORT_DURATION
            doOnStart {
                mark.isVisible = true
            }
            addUpdateListener {
                mark.alpha = it.animatedValue as Float
            }
            start()
        }

        ValueAnimator.ofObject(ArgbEvaluator(), colors.dark, colors.orange).apply {
            duration = AnimationUtils.SHORT_DURATION
            addUpdateListener {
                favoriteButton.setColorFilter(it.animatedValue as Int)
            }
            doOnEnd {
                favoriteButton.isSelected = true
            }
            start()
        }
    }

    private fun unmarkAsFavorite() {
        ValueAnimator.ofFloat(1f, 0f).apply {
            duration = AnimationUtils.SHORT_DURATION
            addUpdateListener {
                mark.alpha = it.animatedValue as Float
            }
            doOnEnd {
                mark.isVisible = false
            }
            start()
        }
        ValueAnimator.ofObject(ArgbEvaluator(), colors.orange, colors.white).apply {
            duration = AnimationUtils.SHORT_DURATION
            addUpdateListener {
                favoriteButton.setColorFilter(it.animatedValue as Int)
            }
            doOnEnd {
                favoriteButton.isSelected = false
                favoriteButton.setColorFilter(colors.dark)
            }
            start()
        }
    }
}