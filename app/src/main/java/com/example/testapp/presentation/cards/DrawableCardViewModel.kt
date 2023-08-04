package com.example.testapp.presentation.cards

import androidx.compose.runtime.Composable

class DrawableCardViewModel(val viewModel: CardViewModel, val composable: @Composable (CardViewModel)->Unit) {
    @Composable
    fun Draw() {
        composable(viewModel)
    }
}