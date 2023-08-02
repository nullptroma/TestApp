package com.example.testapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.testapp.presentation.main.MainScreen
import com.example.testapp.presentation.main.MainViewModel
import com.example.testapp.ui.theme.TestAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
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
    NavHost(navController, startDestination = Route.MAIN_SCREEN.route) {
        composable(route = Route.MAIN_SCREEN.route) {
            val viewModel: MainViewModel = hiltViewModel()
            MainScreen(viewModel.state.value)
        }
        composable(
            route = Route.SELECT_CITY_SCREEN.route
        ) {

        }

    }
}