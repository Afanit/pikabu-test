package ru.aphanite.pikabu_test.ui.fragment

import android.content.Context
import ru.aphanite.pikabu_test.di.activityComponent
import ru.aphanite.pikabu_test.viewmodel.ActivityViewModel

class FeedFragment : StoryFragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)

        activityComponent().feedComponentBuilder()
            .build()
            .inject(this)
    }

    override fun onResume() {
        super.onResume()

        activityViewModel.state = ActivityViewModel.State.FEED
    }

    override fun isActive(): Boolean {
        return activityViewModel.state == ActivityViewModel.State.FEED
    }
}