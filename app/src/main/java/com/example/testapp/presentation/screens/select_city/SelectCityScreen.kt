package com.example.testapp.presentation.screens.select_city

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testapp.domain.models.CityInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCityScreen(vm: SelectCityViewModel, onBack: () -> Unit) {
    val state: SelectCityScreenState = vm.state.value
    if (state.exit) {
        vm.restoreExit()
        onBack()
    }

    var nameFilter by remember {
        mutableStateOf("")
    }
    val lowerCaseName = nameFilter.lowercase()
    var search by remember {
        mutableStateOf(false)
    }
    val focusRequester = remember { FocusRequester() }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    if (!search) {
                        Box(Modifier.weight(.5f), contentAlignment = Alignment.CenterStart) {
                            IconButton(onClick = {
                                onBack()
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color.White
                                )
                            }
                        }
                        Text(
                            text = "Выбор города", color = MaterialTheme.colorScheme.onPrimary
                        )
                        Box(Modifier.weight(.5f), contentAlignment = Alignment.CenterEnd) {
                            IconButton(onClick = {
                                search = true
                            }) {
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = "Search",
                                    tint = Color.White
                                )
                            }
                        }
                    } else {
                        Box(Modifier.weight(.5f), contentAlignment = Alignment.CenterStart) {
                            IconButton(onClick = {
                                search = false
                                nameFilter = ""
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Back",
                                    tint = Color.White
                                )
                            }
                        }
                        Box(contentAlignment = Alignment.CenterStart) {
                            TextField(modifier = Modifier.focusRequester(focusRequester),
                                value = nameFilter,
                                onValueChange = { nameFilter = it })
                        }
                        Box(Modifier.weight(.5f), contentAlignment = Alignment.CenterEnd) {
                            if (nameFilter.isNotEmpty()) IconButton(onClick = { nameFilter = "" }) {
                                Icon(
                                    Icons.Filled.Clear,
                                    contentDescription = "Clear",
                                    tint = Color.White
                                )
                            }
                        }
                        LaunchedEffect(Unit) {
                            focusRequester.requestFocus()
                        }
                    }
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
        )
    }, content = { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            itemsIndexed(state.cities) { index, it ->
                if (!it.name.lowercase().contains(lowerCaseName)) return@itemsIndexed
                Item(it) {
                    vm.selectCity(it)
                }
                Divider(
                    color = Color.LightGray, thickness = 1.dp
                )
            }
        }
    })
}

@Composable
fun Item(item: CityInfo, onClick: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onClick()
        }) {
        Row(
            Modifier.padding(8.dp), horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(Modifier.width(64.dp)) {
                Text(text = item.country, fontSize = 30.sp, color = Color.DarkGray)
            }
            Text(text = item.name, fontSize = 30.sp, color = Color.DarkGray)
        }
    }
}