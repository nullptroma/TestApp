package com.example.testapp.presentation.screens.main

import com.example.testapp.domain.models.EnabledCard

data class MainScreenState(val cards: List<EnabledCard> = listOf(), val loading: Boolean = true)
