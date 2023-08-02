package com.example.testapp.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.testapp.domain.Cards
import com.example.testapp.domain.EnabledCard
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val state: State<MainScreenState>
        get() = _state
    private val _state = mutableStateOf(MainScreenState())

    init {
        _state.value = _state.value.copy(cards = listOf(EnabledCard(Cards.WEATHER, 0)))
    }
}