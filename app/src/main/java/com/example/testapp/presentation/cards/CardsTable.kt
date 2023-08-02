package com.example.testapp.presentation.cards

import androidx.compose.runtime.Composable
import com.example.testapp.domain.Cards
import com.example.testapp.presentation.Route

object CardsTable {
    val table = mapOf(
        Cards.WEATHER to CardView(Route.SELECT_CITY_SCREEN) @Composable { WeatherCard() },
    )
}