package ru.aphanite.pikabu_test.di.component

import dagger.Subcomponent
import ru.aphanite.pikabu_test.di.module.fragment.FeedModule
import ru.aphanite.pikabu_test.di.scope.FragmentScope
import ru.aphanite.pikabu_test.ui.fragment.FeedFragment

@FragmentScope
@Subcomponent(modules = [FeedModule::class])
interface FeedComponent {

    fun inject(fragment: FeedFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): FeedComponent
    }
}