package com.example.testapp.presentation.screens.menu_settings

import androidx.compose.runtime.Immutable
import com.example.testapp.domain.EnabledCard

@Immutable
data class MenuSettingsScreenState(val list: List<EnabledCard> = listOf())
