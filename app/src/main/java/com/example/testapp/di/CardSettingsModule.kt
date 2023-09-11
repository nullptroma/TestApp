package com.example.testapp.di

import com.example.testapp.data.local.repositories.cardsettings.CryptoSettingsRepository
import com.example.testapp.data.local.repositories.cardsettings.MapSettingsRepository
import com.example.testapp.data.local.repositories.cardsettings.WeatherSettingsRepository
import com.example.testapp.domain.models.cardsettings.CryptoSettings
import com.example.testapp.domain.models.cardsettings.MapSettings
import com.example.testapp.domain.models.cardsettings.WeatherSettings
import com.example.testapp.domain.usecases.get_settings.UseCardSettingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CardSettingsModule {
    @Provides
    @Singleton
    fun provideCryptoSettings(repo: CryptoSettingsRepository): UseCardSettingsUseCase<CryptoSettings> {
        return UseCardSettingsUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideMapSettings(repo: MapSettingsRepository): UseCardSettingsUseCase<MapSettings> {
        return UseCardSettingsUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideWeatherSettings(repo: WeatherSettingsRepository): UseCardSettingsUseCase<WeatherSettings> {
        return UseCardSettingsUseCase(repo)
    }
}