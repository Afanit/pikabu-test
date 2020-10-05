package ru.aphanite.pikabu_test.di.module

import androidx.annotation.Nullable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.aphanite.pikabu_test.di.annotation.ViewModelKey
import ru.aphanite.pikabu_test.viewmodel.PreviewViewModel
import ru.aphanite.pikabu_test.viewmodel.ViewModelFactory
import ru.aphanite.pikabu_test.viewmodel.StoryViewModel

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @Nullable
    @ViewModelKey(StoryViewModel::class)
    abstract fun bindStoryViewModel(viewModel: StoryViewModel): ViewModel

    @Binds
    @IntoMap
    @Nullable
    @ViewModelKey(PreviewViewModel::class)
    abstract fun bindPreviewViewModel(viewModel: PreviewViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}