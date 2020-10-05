package ru.aphanite.pikabu_test.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val provider = viewModels[modelClass]
            ?: throw IllegalArgumentException("Model class $modelClass not found")

        return provider.get() as T
    }
}