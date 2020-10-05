package ru.aphanite.pikabu_test.ui.activity

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.aphanite.pikabu_test.R
import ru.aphanite.pikabu_test.databinding.SingleActivityBinding
import ru.aphanite.pikabu_test.di.appComponent
import ru.aphanite.pikabu_test.di.component.ActivityComponent
import ru.aphanite.pikabu_test.utils.AnimationUtils
import ru.aphanite.pikabu_test.ui.activity.SingleScrollDirectionEnforcer.Companion.enforceSingleScrollDirection
import ru.aphanite.pikabu_test.ui.activity.SingleScrollDirectionEnforcer.Companion.recyclerView
import ru.aphanite.pikabu_test.utils.ColorConst
import ru.aphanite.pikabu_test.viewmodel.ActivityViewModel
import javax.inject.Inject

class SingleActivity : AppCompatActivity() {

    lateinit var activityComponent: ActivityComponent

    @Inject
    lateinit var pagerAdapter: FragmentPagerAdapter

    @Inject
    lateinit var colors: ColorConst

    private lateinit var viewModel: ActivityViewModel

    private lateinit var binding: SingleActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        activityComponent = appComponent().activityComponentBuilder()
            .activityContext(this)
            .activity(this)
            .build()
        activityComponent.inject(this)

        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ActivityViewModel::class.java)

        binding = SingleActivityBinding.inflate(layoutInflater)

        binding.viewPager.adapter = pagerAdapter
        // Make diagonal scroll available
        binding.viewPager.recyclerView.enforceSingleScrollDirection()

        binding.tabLayout.addOnTabSelectedListener(TabSelectedListener(colors))
        TabLayoutMediator(binding.tabLayout, binding.viewPager, TabConfigurationStrategy()).attach()

        setContentView(binding.root)
    }

    override fun onBackPressed() {
        with(binding.viewPager) {
            when (viewModel.state) {
                ActivityViewModel.State.FEED -> super.onBackPressed()
                ActivityViewModel.State.FAVORITE -> currentItem -= 1
                ActivityViewModel.State.PREVIEW -> {
                    viewModel.state =
                        if (currentItem == 1) ActivityViewModel.State.FAVORITE
                        else ActivityViewModel.State.FEED
                    supportFragmentManager.popBackStack()
                }
            }
        }
    }

    private class TabConfigurationStrategy : TabLayoutMediator.TabConfigurationStrategy {
        override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.ic_tab_feed)
                }
                1 -> {
                    tab.setIcon(R.drawable.ic_tab_favorite)
                }
            }
        }
    }

    private class TabSelectedListener(private val colors: ColorConst) :
        TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            ValueAnimator.ofObject(ArgbEvaluator(), colors.black, colors.orange).apply {
                duration = AnimationUtils.SHORT_DURATION
                addUpdateListener {
                    tab.icon?.setTint(it.animatedValue as Int)
                }
                start()
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
            ValueAnimator.ofObject(ArgbEvaluator(), colors.orange, colors.black).apply {
                duration = AnimationUtils.SHORT_DURATION
                addUpdateListener {
                    tab.icon?.setTint(it.animatedValue as Int)
                }
                start()
            }
        }

        override fun onTabReselected(tab: TabLayout.Tab) {
            // Do nothing
        }
    }
}