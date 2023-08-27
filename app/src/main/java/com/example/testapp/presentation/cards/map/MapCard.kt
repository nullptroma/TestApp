package com.example.testapp.presentation.cards.map

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.example.testapp.presentation.YandexMap

@Composable
fun MapCard(vm : ViewModel?) {
    if(vm == null || vm !is MapCardViewModel)
        throw Exception()
    val state = vm.state.value

    YandexMap(state.coordinates)
}