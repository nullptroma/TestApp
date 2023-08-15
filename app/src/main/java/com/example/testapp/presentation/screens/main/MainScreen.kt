package com.example.testapp.presentation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.testapp.domain.Cards
import com.example.testapp.presentation.cards.DrawableCardViewModel
import com.example.testapp.presentation.settings.SettingBridge

@ExperimentalMaterial3Api
@Composable
fun MainScreen(
    state: MainScreenState,
    drawableVms: List<DrawableCardViewModel>,
    onCardSetting: (SettingBridge) -> Unit,
    onSetting: () -> Unit
) {
    Column {
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
                            text = AnnotatedString("Править"),
                            onClick = { onSetting() },
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }

                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
        )
        if (!state.loading) {
            LazyColumn {
                itemsIndexed(drawableVms) { index, it ->
                    CardItem(
                        Modifier.padding(5.dp),
                        it,
                        state.cards[index].type,
                        onSetting = onCardSetting
                    )
                }
            }
        } else Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

    }
}

@Composable
fun CardItem(
    modifier: Modifier,
    drawableVm: DrawableCardViewModel,
    type: Cards,
    onSetting: (SettingBridge) -> Unit
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
                Text(text = type.text, Modifier.padding(5.dp))
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

