package com.example.testapp.presentation.cards

import androidx.compose.runtime.Composable
import com.example.testapp.presentation.Route

class DrawableCardViewModel(val viewModel: CardViewModel, val composable: @Composable (CardViewModel)->Unit, val settingRoute: Route) {
    @Composable
    fun Draw() {
        composable(viewModel)
    }
}