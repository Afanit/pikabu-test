package ru.aphanite.pikabu_test.di.component

import dagger.BindsInstance
import dagger.Subcomponent
import ru.aphanite.pikabu_test.di.module.fragment.PreviewModule
import ru.aphanite.pikabu_test.di.qualifier.StoryId
import ru.aphanite.pikabu_test.di.scope.FragmentScope
import ru.aphanite.pikabu_test.ui.fragment.PreviewFragment

@FragmentScope
@Subcomponent(modules = [PreviewModule::class])
interface PreviewComponent {

    fun inject(fragment: PreviewFragment)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun storyId(@StoryId int: Long): Builder

        fun build(): PreviewComponent
    }
}