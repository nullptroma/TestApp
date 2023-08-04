package com.example.testapp.presentation.cards

import androidx.compose.runtime.Composable
import com.example.testapp.presentation.Route

data class CardView(
    val settingsRoute: Route,
    val viewModelFactory: @Composable (Long) -> CardViewModel,
    val composable: @Composable (CardViewModel) -> Unit,
)