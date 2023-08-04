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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testapp.domain.Cards
import com.example.testapp.presentation.cards.CardsTable

@Composable
fun MainScreen(state: MainScreenState) {
    LazyColumn {
        items(state.cards) {
            CardItem(Modifier, it.id, it.type)
        }
    }
}

@Composable
fun CardItem(modifier: Modifier, id: Long, type: Cards) {
    val tableItem = CardsTable.table[type] ?: return
    val vm = tableItem.viewModelFactory(id)
    Card(modifier = modifier.fillMaxWidth()) {
        Column {
            Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = type.name, Modifier.padding(5.dp))
                Button(modifier = Modifier
                    .padding(5.dp)
                    .height(20.dp),onClick = { /*TODO*/ }) {
                    Text(text = "...")
                }
            }
            Box(modifier= Modifier
                .fillMaxWidth()
                .padding(12.dp), contentAlignment = Alignment.Center){
                tableItem.composable(vm)
            }
        }
    }
}

