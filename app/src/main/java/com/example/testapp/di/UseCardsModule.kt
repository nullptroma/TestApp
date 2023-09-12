package com.example.testapp.di

import com.example.testapp.domain.CardType
import com.example.testapp.domain.models.UseCardsContainer
import com.example.testapp.domain.models.cardsettings.CardSettings
import com.example.testapp.domain.usecases.cards.UseCardUseCase
import com.example.testapp.domain.usecases.cards.UseCryptoCardUseCase
import com.example.testapp.domain.usecases.cards.UseMapCardUseCase
import com.example.testapp.domain.usecases.cards.UseWeatherCardUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCardsModule {

    @Singleton
    @Provides
    @Suppress("UNCHECKED_CAST")
    fun provideCardsList(
        weather: UseWeatherCardUseCase,
        map: UseMapCardUseCase,
        crypto: UseCryptoCardUseCase,
    ): UseCardsContainer {
        return UseCardsContainer(listOf(
            weather as UseCardUseCase<CardSettings> to CardType.WEATHER,
            map as UseCardUseCase<CardSettings> to CardType.MAP,
            crypto as UseCardUseCase<CardSettings> to CardType.CRYPTO,
        ))
    }
}