package com.example.testapp.di

import com.example.testapp.presentation.viewmodels.cards.CryptoCardViewModel
import com.example.testapp.presentation.viewmodels.cards.MapCardViewModel
import com.example.testapp.presentation.viewmodels.cards.WeatherCardViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun weatherCardViewModelFactory(): WeatherCardViewModel.Factory
    fun mapCardViewModelFactory(): MapCardViewModel.Factory
    fun cryptoCardViewModelFactory(): CryptoCardViewModel.Factory
}