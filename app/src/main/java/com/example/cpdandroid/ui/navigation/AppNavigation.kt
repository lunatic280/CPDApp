package com.example.cpdandroid.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cpdandroid.ui.screen.BlogScreen
import com.example.cpdandroid.ui.screen.TestMainScreen

sealed class Screen(val route: String) {
    object Home : Screen("Home")
    object  Blog : Screen("Blog")
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "Home") {
        composable("Home") { TestMainScreen(navController) }
        composable("Blog") { BlogScreen(navController) }
    }
}