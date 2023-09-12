package com.example.testapp.domain.models.cards_callbacks

class WeatherCallback(val refresh: (id : Long) -> Unit) : ICardCallback