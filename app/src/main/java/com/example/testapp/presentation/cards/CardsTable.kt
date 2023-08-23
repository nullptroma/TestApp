package com.example.testapp.presentation.cards

import com.example.testapp.domain.Cards
import com.example.testapp.presentation.Route
import com.example.testapp.presentation.cards.crypto.CryptoCard
import com.example.testapp.presentation.cards.crypto.cryptoCardViewModel
import com.example.testapp.presentation.cards.map.MapCard
import com.example.testapp.presentation.cards.map.mapCardViewModel
import com.example.testapp.presentation.cards.weather.WeatherCard
import com.example.testapp.presentation.cards.weather.weatherCardViewModel

object CardsTable {
    val table = mapOf(
        Cards.WEATHER to CardView(Route.SELECT_CITY_SCREEN,
            composable = { vm -> WeatherCard(vm) },
            viewModelFactory = { id -> weatherCardViewModel(id) }),
        Cards.CRYPTO to CardView(Route.SELECT_CRYPTOS_SCREEN,
            composable = { vm -> CryptoCard(vm) },
            viewModelFactory = { id -> cryptoCardViewModel(id) }),
        Cards.MAP to CardView(Route.SELECT_CITY_SCREEN,
            composable = { vm -> MapCard(vm) },
            viewModelFactory = { id -> mapCardViewModel(id) })
    )
}