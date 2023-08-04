package com.example.testapp.presentation.cards

import androidx.compose.runtime.Composable
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import com.example.testapp.presentation.Route
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory

data class CardView(
    val settingsRoute: Route,
    val composable: @Composable (CardViewModel) -> Unit,
    val viewModelFactory: @Composable (Long)->CardViewModel
)