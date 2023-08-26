@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.testapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testapp.domain.models.cardsettings.EnabledCard
import com.example.testapp.presentation.cards.CardsTable
import com.example.testapp.presentation.cards.DrawableCardViewModel
import com.example.testapp.presentation.screens.main.MainScreen
import com.example.testapp.presentation.screens.main.MainViewModel
import com.example.testapp.presentation.screens.menu_settings.MenuSettingsViewModel
import com.example.testapp.presentation.screens.select_city.SelectCityScreen
import com.example.testapp.presentation.screens.select_city.SelectCityViewModel
import com.example.testapp.presentation.screens.select_cryptos.SelectCryptosScreen
import com.example.testapp.presentation.screens.select_cryptos.SelectCryptosViewModel
import com.example.testapp.presentation.settings.CitySettingBridge
import com.example.testapp.presentation.settings.CryptosSettingBridge
import com.example.testapp.ui.theme.TestAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.testapp.presentation.screens.menu_settings.MenuSettings as MenuSettings1

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    TestApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestApp() {
    val navController = rememberNavController()

    val mainViewModel: MainViewModel = hiltViewModel()
    val mainScreenState = mainViewModel.state.value
    val vms = mainScreenState.cards.map { it.toDrawableCardViewModel() }

    val selectCityViewModel: SelectCityViewModel = hiltViewModel()
    val menuSettingsViewModel: MenuSettingsViewModel = hiltViewModel()
    val cryptosSettingsViewModel: SelectCryptosViewModel = hiltViewModel()

    NavHost(navController, startDestination = Route.MAIN_SCREEN.path) {
        composable(route = Route.MAIN_SCREEN.path) {
            MainScreen(mainScreenState, vms, onCardSetting = { bridge, route ->
                if(navController.currentBackStackEntry?.destination?.route != Route.MAIN_SCREEN.path)
                    return@MainScreen
                if (route == Route.SELECT_CITY_SCREEN && bridge is CitySettingBridge) {
                    selectCityViewModel.setBridge(bridge)
                    navController.navigate(route.path)
                } else if (route == Route.SELECT_CRYPTOS_SCREEN && bridge is CryptosSettingBridge) {
                    cryptosSettingsViewModel.setBridge(bridge)
                    navController.navigate(route.path)
                }
            }, onSetting = {
                navController.navigate(Route.MAIN_MENU_SETTINGS.path)
                menuSettingsViewModel.refresh()
            })
        }
        composable(
            route = Route.SELECT_CITY_SCREEN.path
        ) {
            SelectCityScreen(vm = selectCityViewModel) {
                navController.navigateUp()
            }
        }

        composable(
            route = Route.SELECT_CRYPTOS_SCREEN.path
        ) {
            SelectCryptosScreen(vm = cryptosSettingsViewModel) {
                navController.navigateUp()
            }
        }

        composable(
            route = Route.MAIN_MENU_SETTINGS.path
        ) {
            MenuSettings1(vm = menuSettingsViewModel) {
                navController.navigateUp()
            }
        }
    }
}

@Composable
fun EnabledCard.toDrawableCardViewModel(): DrawableCardViewModel {
    val tableItem = CardsTable.table[this.type]!!
    return DrawableCardViewModel(
        tableItem.viewModelFactory(this.id), tableItem.composable, tableItem.settingsRoute
    )
}