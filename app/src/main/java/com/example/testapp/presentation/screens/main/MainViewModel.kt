package com.example.testapp.presentation.screens.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.MyObserver
import com.example.testapp.data.local.repositories.EnabledCardsRepository
import com.example.testapp.domain.models.cardsettings.EnabledCard
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repo: EnabledCardsRepository,
) : ViewModel() {
    val state: State<MainScreenState>
        get() = _state
    private val _state = mutableStateOf(MainScreenState())
    private val liveData: LiveData<List<EnabledCard>> = repo.getAll()
    private val observer: MyObserver<List<EnabledCard>> = MyObserver {
        _state.value = _state.value.copy(cards = it, loading = false)
    }

    init {
        liveData.observeForever(observer)
    }

    override fun onCleared() {
        super.onCleared()
        liveData.removeObserver(observer)
    }
}