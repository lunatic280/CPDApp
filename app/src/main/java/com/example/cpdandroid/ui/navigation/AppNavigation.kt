package com.example.cpdandroid.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cpdandroid.model.AuthViewModel
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
    object Blog : Screen("Blog")
    object Counter : Screen("Counter")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // 상단에서 단일 AuthViewModel, BlogViewModel 인스턴스 생성
    val authViewModel: AuthViewModel = viewModel()
    val blogViewModel: BlogViewModel = viewModel()

    NavHost(navController, startDestination = "login") {
        // 로그인 화면: 동일한 authViewModel 사용
        composable("login") {
            LoginScreen(
                viewModel = authViewModel,
                navController = navController,
                onSignupSuccess = { navController.navigate("Home") }
            )
        }

        composable("Home") {
            TestMainScreen(navController)
        }

        composable("Blog") {
            BlogScreen(
                viewModel = blogViewModel,
                navController = navController
            )
        }

        // CreateViewScreen 호출 시 authViewModel에서 로그인 정보 사용
        composable("CreateView") {
            CreateViewScreen(
                viewModel     = blogViewModel,
                navController = navController,
                authorEmail   = authViewModel.userEmail.value ?: "",
                authorName    = authViewModel.userName.value  ?: ""
            )
        }

        composable(
            "BlogDetail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: return@composable
            BlogDetailScreen(
                id = id,
                viewModel = blogViewModel,
                navController = navController
            )
        }

        composable(
            "BlogDetail/update/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            BlogUpdateScreen(
                id = id,
                viewModel = blogViewModel,
                navController = navController
            )
        }

        composable("signup") {
            SignupScreen(
                viewModel = authViewModel,
                navController = navController,
                onSignupSuccess = { navController.navigate("login") }
            )
        }
    }
}