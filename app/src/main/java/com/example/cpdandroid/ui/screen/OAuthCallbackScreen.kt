//package com.example.cpdandroid.ui.screen
//
//import android.net.Uri
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import com.example.cpdandroid.model.AuthViewModel
//
//@Composable
//fun OAuthCallbackScreen(
//    uri: Uri?,
//    navController: NavController,
//    authViewModel: AuthViewModel = viewModel()
//) {
//    LaunchedEffect(uri) {
//        uri?.let { deepLink ->
//            // AuthViewModel.handleKakaoCallback 은 Uri 하나만 받고
//            // 내부에서 access/refresh 토큰을 추출하도록 되어 있습니다 :contentReference[oaicite:0]{index=0}:contentReference[oaicite:1]{index=1}
//            authViewModel.handleKakaoCallback(deepLink) {
//                navController.navigate("Home") {
//                    popUpTo("login") { inclusive = true }
//                }
//            }
//        }
//    }
//
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        CircularProgressIndicator()
//    }
//}