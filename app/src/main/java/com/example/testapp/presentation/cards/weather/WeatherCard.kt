package com.example.testapp.presentation.cards.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel

@Composable
fun WeatherCard(vm : ViewModel?) {
    if(vm == null || vm !is WeatherCardViewModel)
        throw Exception()
    val state = vm.state.value

    Column {
        Text(text = state.city)
        Button(onClick = { vm.test() }) {
            Text(text = "Button")
        }
    }
}