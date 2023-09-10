package com.example.testapp.presentation.states.cards

import com.example.testapp.domain.CardType
import com.example.testapp.domain.models.Weather

data class WeatherCardState(val data: Weather? = null): CardState(CardType.WEATHER)