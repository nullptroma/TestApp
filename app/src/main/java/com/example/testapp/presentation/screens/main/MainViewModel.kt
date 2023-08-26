package com.example.testapp.presentation.screens.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.MyObserver
import com.example.testapp.domain.models.cardsettings.EnabledCard
import com.example.testapp.domain.usecases.GetLiveEnabledCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLiveEnabledCardsUseCase: GetLiveEnabledCardsUseCase
) : ViewModel() {
    val state: State<MainScreenState>
        get() = _state
    private val _state = mutableStateOf(MainScreenState())
    private val observer: MyObserver<List<EnabledCard>> = MyObserver {
        _state.value = _state.value.copy(cards = it, loading = false)
    }

    init {
        getLiveEnabledCardsUseCase.liveData.observeForever(observer)
    }

    override fun onCleared() {
        super.onCleared()
        getLiveEnabledCardsUseCase.liveData.removeObserver(observer)
    }
}