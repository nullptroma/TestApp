package com.example.testapp.data.local.repositories

import com.example.testapp.data.local.repositories.cardsettings.CryptoSettingsRepository
import com.example.testapp.data.local.repositories.cardsettings.MapSettingsRepository
import com.example.testapp.data.local.repositories.cardsettings.WeatherSettingsRepository
import com.example.testapp.domain.CardType
import com.example.testapp.domain.models.cardsettings.EnabledCard
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsCreator @Inject constructor(
    private val _weatherRepo: WeatherSettingsRepository,
    private val _mapRepo: MapSettingsRepository,
    private val _cryptoRepo: CryptoSettingsRepository
) {
    suspend fun createSettings(cards : List<EnabledCard>) {
        val weathers = cards.filter { it.type == CardType.WEATHER }.map { it.id }
        val maps = cards.filter { it.type == CardType.MAP }.map { it.id }
        val cryptos = cards.filter { it.type == CardType.CRYPTO }.map { it.id }

        _weatherRepo.checkIds(weathers)
        _mapRepo.checkIds(maps)
        _cryptoRepo.checkIds(cryptos)
    }
}