package com.example.testapp.presentation.screens.menu_settings

import androidx.compose.runtime.Immutable
import com.example.testapp.domain.models.EnabledCard

@Immutable
data class MenuSettingsScreenState(val list: List<EnabledCard> = listOf())
