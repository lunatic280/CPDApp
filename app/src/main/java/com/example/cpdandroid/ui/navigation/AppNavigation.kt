package com.example.cpdandroid.ui.navigation


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.material3.CircularProgressIndicator
import com.example.cpdandroid.model.DogViewModel
import com.example.cpdandroid.ui.screen.DogCreateScreen
import com.example.cpdandroid.ui.screen.DogListScreen
import com.example.cpdandroid.ui.screen.LocationTrackerScreen

// ★ RetrofitClient 싱글톤 import 추가
import com.example.app.network.RetrofitClient


sealed class Screen(val route: String) {
    object Home : Screen("Home")
    object Blog : Screen("Blog")
    object Counter : Screen("Counter")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val blogViewModel: BlogViewModel = viewModel()
    val dogViewModel: DogViewModel = viewModel()

    // 이 한 줄만 있으면 됨 (싱글톤으로 재사용)
    val apiService = RetrofitClient.api

    NavHost(navController, startDestination = "login") {
        // 로그인 화면
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
                blogViewModel = blogViewModel,
                authViewModel = authViewModel,
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
                navController = navController,
                authorEmail   = authViewModel.userEmail.value ?: "",
                authorName    = authViewModel.userName.value  ?: ""
            )
        }

        // 삭제용 목적지 등록
        composable(
            "BlogDetail/delete/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: return@composable
            LaunchedEffect(id) {
                blogViewModel.deleteBlog(id)
                navController.popBackStack("Blog", inclusive = false)
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        composable("signup") {
            SignupScreen(
                viewModel = authViewModel,
                navController = navController,
                onSignupSuccess = { navController.navigate("login") }
            )
        }

        composable("DogList") {
            DogListScreen(
                navController = navController,
                dogViewModel = dogViewModel,
                authViewModel = authViewModel
            )
        }
        composable("DogCreate") {
            DogCreateScreen(
                navController = navController,
                authViewModel = authViewModel,   // 반드시 전달
                dogViewModel = dogViewModel      // 반드시 전달
            )
        }
        composable("Tracking") {
            LocationTrackerScreen(api = apiService)
        }
    }
}
