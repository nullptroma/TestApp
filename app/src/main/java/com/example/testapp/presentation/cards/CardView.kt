package com.example.testapp.presentation.cards

import androidx.compose.runtime.Composable
import com.example.testapp.presentation.Route

data class CardView(
    val settingsRoute: Route = Route.NONE,
    val composable: @Composable () -> Unit = { }
)