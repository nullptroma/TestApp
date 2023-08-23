package com.example.testapp.presentation.screens.selectcity

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SelectCityScreen(vm:SelectCityViewModel, onExit:()->Unit) {
    val state: SelectCityScreenState = vm.state.value
    if(state.exit)
    {
        vm.restoreExit()
        onExit()
    }
    Column {
        Button(onClick = {
            vm.selectCity("1")
        }) {
            Text(text = "1")
        }
        Button(onClick = {
            vm.selectCity("22")
        }) {
            Text(text = "22")
        }
        Button(onClick = {
            vm.selectCity("333")
        }) {
            Text(text = "333")
        }
    }
}