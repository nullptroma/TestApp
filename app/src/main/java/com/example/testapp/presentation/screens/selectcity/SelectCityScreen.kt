package com.example.testapp.presentation.screens.selectcity

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SelectCityScreen(state: SelectCityScreenState, onExit:()->Unit, onSelect:(String)->Unit) {
    if(state.exit)
        onExit()
    Column {
        Button(onClick = {
            onSelect("1")
        }) {
            Text(text = "1")
        }
        Button(onClick = {
            onSelect("22")
        }) {
            Text(text = "22")
        }
        Button(onClick = {
            onSelect("333")
        }) {
            Text(text = "333")
        }
    }
}