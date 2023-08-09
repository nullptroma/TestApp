package com.example.testapp.presentation.screens.menusettings

import androidx.compose.runtime.Immutable
import com.example.testapp.domain.EnabledCard

@Immutable
data class MenuSettingsScreenState(val list: List<EnabledCard> = listOf())
