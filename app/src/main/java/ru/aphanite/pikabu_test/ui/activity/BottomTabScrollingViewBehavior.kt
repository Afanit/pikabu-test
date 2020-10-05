package ru.aphanite.pikabu_test.ui.activity

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import ru.aphanite.pikabu_test.utils.AnimationUtils

class BottomTabScrollingViewBehavior(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<ViewPager2>(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: ViewPager2,
        dependency: View
    ): Boolean {
        return dependency is TabLayout
    }

    private var isAnimationEnd = true

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: ViewPager2,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
       return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: ViewPager2,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        if (!isAnimationEnd) {
            return
        }

        if (dyConsumed > 0) {
            hideSmoothly(coordinatorLayout, child)
        } else if (dyConsumed < 0) {
            showSmoothly(coordinatorLayout, child)
        }
    }

    private fun hideSmoothly(coordinatorLayout: CoordinatorLayout, child: ViewPager2) {
        coordinatorLayout.getDependencies(child).map { view ->
            if (!view.isVisible || view !is TabLayout) {
                return@map
            }

            ValueAnimator.ofInt(0, view.height).apply {
                duration = AnimationUtils.SHORT_DURATION
                doOnStart {
                    isAnimationEnd = false
                }
                addUpdateListener {
                    view.translationY = (it.animatedValue as Int).toFloat()
                }
                doOnEnd {
                    view.isVisible = false
                    isAnimationEnd = true
                }
                start()
            }
        }
    }

    private fun showSmoothly(coordinatorLayout: CoordinatorLayout, child: ViewPager2) {
        coordinatorLayout.getDependencies(child).map { view ->
            if (view.isVisible || view !is TabLayout) {
                return@map
            }

            ValueAnimator.ofInt(view.height, 0).apply {
                duration = AnimationUtils.SHORT_DURATION
                doOnStart {
                    isAnimationEnd = false
                    view.isVisible = true
                }
                addUpdateListener {
                    view.translationY = (it.animatedValue as Int).toFloat()
                }
                doOnEnd {
                    isAnimationEnd = true
                }
                start()
            }
        }
    }
}