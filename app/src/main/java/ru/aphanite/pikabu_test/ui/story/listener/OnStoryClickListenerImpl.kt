package ru.aphanite.pikabu_test.ui.story.listener

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import ru.aphanite.pikabu_test.R
import ru.aphanite.pikabu_test.di.qualifier.ActivityContext
import ru.aphanite.pikabu_test.model.StoryModel
import ru.aphanite.pikabu_test.ui.fragment.PreviewFragment
import javax.inject.Inject


class OnStoryClickListenerImpl @Inject constructor(
    @ActivityContext private val context: Context,
    private val layoutManager: LinearLayoutManager,
    private val activity: AppCompatActivity
) :
    OnStoryClickListener {

    override fun onAction(data: StoryModel, view: ConstraintLayout, adapterPosition: Int) {

        val smoothScroller: SmoothScroller =
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference(): Int {
                    return SNAP_TO_START
                }

                override fun onStop() {
                    super.onStop()

                    activity.supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.fragment_fade_enter,
                            R.anim.fragment_fade_exit,
                            R.anim.fragment_fade_enter,
                            R.anim.fragment_fade_exit
                        )
                        .replace(R.id.container, PreviewFragment.newInstance(data.id))
                        .addToBackStack(null)
                        .commit()
                }
            }

        smoothScroller.targetPosition = adapterPosition

        layoutManager.startSmoothScroll(smoothScroller)
    }
}