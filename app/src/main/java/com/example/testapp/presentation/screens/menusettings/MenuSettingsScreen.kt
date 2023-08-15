package com.example.testapp.presentation.screens.menusettings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.testapp.domain.Cards
import com.example.testapp.presentation.drag.ReorderableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuSettings(vm: MenuSettingsViewModel, onBack: () -> Unit) {
    val state = vm.state.value
    var dialogOpen by remember {
        mutableStateOf(false)
    }

    if (dialogOpen) {
        Dialog(onDismissRequest = {
            dialogOpen = false
        }) {
            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text(text = "Выберите тип карточки:")
                    Spacer(Modifier)
                    LazyColumn {
                        items(Cards.values()) {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    dialogOpen = false
                                    vm.createCard(it)
                                }) {
                                Text(text = it.text, modifier = Modifier.padding(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Spacer(modifier = Modifier.weight(.5f))
                        Text(text = "Главный экран", color = MaterialTheme.colorScheme.onPrimary)
                        Box(Modifier.weight(.5f), contentAlignment = Alignment.CenterEnd) {
                            ClickableText(
                                modifier = Modifier.padding(end = 8.dp),
                                text = AnnotatedString("Готово"),
                                onClick = { onBack() },
                                style = TextStyle(
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }

                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        content = {
        ItemsCard(Modifier.padding(it),
            state,
            move = { from, to -> vm.move(from, to) },
            removeCard = { id -> vm.removeCard(id) })
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            dialogOpen = true
        }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
        }
    })
}

@Composable
fun ItemsCard(
    modifier: Modifier,
    state: MenuSettingsScreenState,
    move: (Int, Int) -> Unit,
    removeCard: (Long) -> Unit
) {
    Card(
        modifier = modifier.padding(8.dp), elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {

        ReorderableList(state.list, { from, to ->
            move(from, to)
        }) { index, it ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = it.type.text, modifier = Modifier.padding(start = 8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "icon")
                    IconButton(onClick = { removeCard(it.id) }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "icon")
                    }
                }
            }
            if (index != state.list.size-1) Divider(color = Color.Black, thickness = 0.5.dp)
        }
    }
}