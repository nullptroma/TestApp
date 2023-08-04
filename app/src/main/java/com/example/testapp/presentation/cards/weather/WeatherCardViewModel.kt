package com.example.testapp.presentation.cards.weather

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testapp.data.local.repositories.WeatherSettingsRepository
import com.example.testapp.di.ViewModelFactoryProvider
import com.example.testapp.domain.Cards
import com.example.testapp.presentation.cards.CardViewModel
import com.example.testapp.presentation.screens.main.MainScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors

class WeatherCardViewModel @AssistedInject constructor(
    private val repo: WeatherSettingsRepository,
    @Assisted override val id: Long
) : CardViewModel() {
    override val cardType: Cards
        get() = Cards.WEATHER

    val state: State<WeatherCardState>
        get() = _state
    private val _state = mutableStateOf(WeatherCardState())

    init {
        _state.value = _state.value.copy(city = "Pyatigorsk: $id")
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Long): WeatherCardViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory, // this is the Factory interface
            id: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(id) as T
            }
        }
    }
}

@Composable
fun weatherCardViewModel(id: Long): WeatherCardViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity, ViewModelFactoryProvider::class.java
    ).weatherCardViewModelFactory()

    return viewModel(
        factory = WeatherCardViewModel.provideFactory(factory, id), key = id.toString()
    )
}