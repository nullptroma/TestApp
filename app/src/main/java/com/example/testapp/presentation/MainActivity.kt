@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.testapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testapp.domain.EnabledCard
import com.example.testapp.presentation.cards.CardsTable
import com.example.testapp.presentation.cards.DrawableCardViewModel
import com.example.testapp.presentation.screens.main.MainScreen
import com.example.testapp.presentation.screens.main.MainViewModel
import com.example.testapp.presentation.screens.menusettings.MenuSettings
import com.example.testapp.presentation.screens.menusettings.MenuSettingsViewModel
import com.example.testapp.presentation.screens.selectcity.SelectCityScreen
import com.example.testapp.presentation.screens.selectcity.SelectCityViewModel
import com.example.testapp.presentation.settings.CitySettingBridge
import com.example.testapp.presentation.settings.SettingBridge
import com.example.testapp.ui.theme.TestAppTheme
import dagger.hilt.android.AndroidEntryPoint

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

@Composable
fun TestApp() {
    val navController = rememberNavController()
    var curSettingBridge: SettingBridge? = null

    val mainViewModel: MainViewModel = hiltViewModel()
    val mainScreenState = mainViewModel.state.value
    val vms = mainScreenState.cards.map { it.toDrawableCardViewModel() }

    val selectCityViewModel: SelectCityViewModel = hiltViewModel()
    val menuSettingsViewModel: MenuSettingsViewModel = hiltViewModel()

    NavHost(navController, startDestination = Route.MAIN_SCREEN.path) {
        composable(route = Route.MAIN_SCREEN.path) {
            MainScreen(mainScreenState, vms, onCardSetting = { bridge ->
                curSettingBridge = bridge
                navController.navigate(bridge.route.path)
            }, onSetting = {
                navController.navigate(Route.MAIN_MENU_SETTINGS.path)
                menuSettingsViewModel.refresh()
            })
        }
        composable(
            route = Route.SELECT_CITY_SCREEN.path
        ) {
            val bridge: CitySettingBridge =
                if (curSettingBridge != null && curSettingBridge is CitySettingBridge) (curSettingBridge as CitySettingBridge)
                else return@composable
            selectCityViewModel.setBridge(bridge)
            SelectCityScreen(selectCityViewModel.state.value, onExit = {
                navController.navigateUp()
                selectCityViewModel.restoreExit()
            }, onSelect = { city ->
                selectCityViewModel.selectCity(city)
            })
        }

        composable(
            route = Route.SELECT_CRYPTOS_SCREEN.path
        ) {
            Log.d("MyTag", "Отрисовка крипты")
            Text(text = "Crypto settings")
        }

        composable(
            route = Route.MAIN_MENU_SETTINGS.path
        ) {
            MenuSettings(menuSettingsViewModel) {
                menuSettingsViewModel.save()
                navController.navigateUp()
            }
        }
    }
}

@Composable
fun EnabledCard.toDrawableCardViewModel(): DrawableCardViewModel {
    val tableItem = CardsTable.table[this.type]!!
    return DrawableCardViewModel(tableItem.viewModelFactory(this.id), tableItem.composable)
}