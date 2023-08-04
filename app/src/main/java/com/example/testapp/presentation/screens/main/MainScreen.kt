package com.example.testapp.presentation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testapp.presentation.cards.DrawableCardViewModel
import com.example.testapp.presentation.settings.SettingBridge

@Composable
fun MainScreen(
    state: MainScreenState,
    drawableVms: List<DrawableCardViewModel>,
    onSetting: (SettingBridge) -> Unit
) {
    LazyColumn {
        items(drawableVms) {
            CardItem(Modifier.padding(5.dp), it, onSetting = onSetting)
        }
    }
}

@Composable
fun CardItem(
    modifier: Modifier, drawableVm: DrawableCardViewModel, onSetting: (SettingBridge) -> Unit
) {
    val vm = drawableVm.viewModel
    Card(
        modifier = modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = vm.cardType.text, Modifier.padding(5.dp))
                Button(modifier = Modifier
                    .padding(5.dp)
                    .height(20.dp), onClick = {
                    onSetting(vm.createSettingBridge())
                }) {
                    Text(text = "...")
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                drawableVm.Draw()
            }
        }
    }
}

