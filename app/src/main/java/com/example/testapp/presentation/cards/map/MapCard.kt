package com.example.testapp.presentation.cards.map

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel

@Composable
fun MapCard(vm : ViewModel?) {
    if(vm == null || vm !is MapCardViewModel)
        throw Exception()
    val state = vm.state.value

    Column {
        Text(text = "Map: ${state.city}")
    }
}