package com.example.testapp.presentation.states.screens

import com.example.testapp.domain.models.cardsettings.EnabledCard

data class MainScreenState(val cards: List<EnabledCard> = listOf(), val loading: Boolean = true)
