package ru.aphanite.pikabu_test.viewmodel

import androidx.lifecycle.ViewModel

class ActivityViewModel : ViewModel() {

    var state = State.FEED

    enum class State {
        FEED, FAVORITE, PREVIEW
    }
}