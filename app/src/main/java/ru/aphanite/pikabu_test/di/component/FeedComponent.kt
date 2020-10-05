package ru.aphanite.pikabu_test.di.component

import dagger.Subcomponent
import ru.aphanite.pikabu_test.di.scope.FragmentScope
import ru.aphanite.pikabu_test.di.module.FeedModule
import ru.aphanite.pikabu_test.ui.main.FeedAdapter
import ru.aphanite.pikabu_test.ui.main.FeedFragment
import ru.aphanite.pikabu_test.ui.main.FeedOnFavoriteClickListener

@FragmentScope
@Subcomponent(modules = [FeedModule::class])
interface FeedComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): FeedComponent
    }

    fun favoriteListener(): FeedOnFavoriteClickListener
    fun feedAdapter(): FeedAdapter

    fun inject(fragment: FeedFragment)
}