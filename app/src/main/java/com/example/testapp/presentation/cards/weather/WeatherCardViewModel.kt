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
import com.example.testapp.domain.usecases.ChangeWeatherCityUseCase
import com.example.testapp.presentation.cards.CardViewModel
import com.example.testapp.presentation.settings.CitySettingBridge
import com.example.testapp.presentation.settings.SettingBridge
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors

class WeatherCardViewModel @AssistedInject constructor(
    private val _changeCityUseCase: ChangeWeatherCityUseCase,
    private val _repo: WeatherSettingsRepository,
    @Assisted id: Long
) : CardViewModel() {

    override val id: Long
        get() = _id
    private var _id:Long

    val state: State<WeatherCardState>
        get() = _state
    private val _state = mutableStateOf(WeatherCardState())
    private lateinit var _city:String

    init {
        _id = id
    }

    fun test() {
        _state.value = _state.value.copy(city="Test!!!")
    }

    override fun createSettingBridge(): SettingBridge {
        return CitySettingBridge { city ->
            Log.d("MyTag", "New city for $id is $city")
            _state.value = _state.value.copy(city=city)
        }
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