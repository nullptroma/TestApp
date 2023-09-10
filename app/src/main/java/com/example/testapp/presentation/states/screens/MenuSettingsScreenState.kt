package com.example.testapp.presentation.states.screens

import androidx.compose.runtime.Immutable
import com.example.testapp.domain.models.cardsettings.EnabledCard

@Immutable
data class MenuSettingsScreenState(val list: List<EnabledCard> = listOf())
