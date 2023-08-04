package com.example.testapp.di

import com.example.testapp.presentation.cards.weather.WeatherCardViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun weatherCardViewModelFactory(): WeatherCardViewModel.Factory
}