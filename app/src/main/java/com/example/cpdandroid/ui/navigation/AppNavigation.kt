package com.example.cpdandroid.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cpdandroid.model.BlogViewModel
import com.example.cpdandroid.ui.screen.BlogScreen
import com.example.cpdandroid.ui.screen.CounterScreen
import com.example.cpdandroid.ui.screen.CreateViewScreen
import com.example.cpdandroid.ui.screen.TestMainScreen

sealed class Screen(val route: String) {
    object Home : Screen("Home")
    object  Blog : Screen("Blog")
    object Counter : Screen("Counter")
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "Home") {
        composable("Home") { TestMainScreen(navController) }
        composable("Blog") { BlogScreen(navController) }
        composable("Counter") { CounterScreen(viewModel = viewModel(), navController) }
        composable("CreateView") { CreateViewScreen(viewModel = viewModel(), navController)}
    }
}