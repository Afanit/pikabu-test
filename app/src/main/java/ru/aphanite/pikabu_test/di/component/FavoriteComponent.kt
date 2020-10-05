package ru.aphanite.pikabu_test.di.component

import dagger.Subcomponent
import ru.aphanite.pikabu_test.di.module.fragment.FavoriteModule
import ru.aphanite.pikabu_test.di.scope.FragmentScope
import ru.aphanite.pikabu_test.ui.fragment.FavoriteFragment

@FragmentScope
@Subcomponent(modules = [FavoriteModule::class])
interface FavoriteComponent {

    fun inject(fragment: FavoriteFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): FavoriteComponent
    }
}