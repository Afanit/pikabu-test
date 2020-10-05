package ru.aphanite.pikabu_test.ui.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.aphanite.pikabu_test.ui.fragment.FavoriteFragment
import ru.aphanite.pikabu_test.ui.fragment.FeedFragment
import java.lang.IllegalStateException
import javax.inject.Inject

class FragmentPagerAdapter @Inject constructor(activity: AppCompatActivity) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = PAGE_NUMBER

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FeedFragment()
            1 -> FavoriteFragment()
            else -> throw IllegalStateException("No page with number $position")
        }
    }

    private companion object {
        private const val PAGE_NUMBER = 2
    }
}