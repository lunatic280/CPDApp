package com.example.cpdandroid.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cpdandroid.model.BlogViewModel
import com.example.cpdandroid.ui.screen.BlogDetailScreen
import com.example.cpdandroid.ui.screen.BlogScreen
import com.example.cpdandroid.ui.screen.BlogUpdateScreen
import com.example.cpdandroid.ui.screen.CounterScreen
import com.example.cpdandroid.ui.screen.CreateViewScreen
import com.example.cpdandroid.ui.screen.LoginScreen
import com.example.cpdandroid.ui.screen.SignupScreen
import com.example.cpdandroid.ui.screen.TestMainScreen

sealed class Screen(val route: String) {
    object Home : Screen("Home")
    object  Blog : Screen("Blog")
    object Counter : Screen("Counter")
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "login") {
        composable("Home") { TestMainScreen(navController) }
        composable("Blog") { BlogScreen(viewModel = viewModel(), navController) }
        composable("Counter") { CounterScreen(viewModel = viewModel(), navController) }
        composable("CreateView") { CreateViewScreen(viewModel = viewModel(), navController)}
        composable(
            "BlogDetail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })) {
            backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            BlogDetailScreen(id, viewModel = viewModel(), navController)
        }
        composable(
            "BlogDetail/update/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })) {
                backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            BlogUpdateScreen(id, viewModel = viewModel(), navController)
        }
        composable("signup") { SignupScreen(
            viewModel = viewModel(),
            navController,
            onSignupSuccess = { navController.navigate("login") }) }
        composable("login") { LoginScreen(
            viewModel = viewModel(),
            navController,
            onSignupSuccess = { navController.navigate("Home") }
        )}
    }
}