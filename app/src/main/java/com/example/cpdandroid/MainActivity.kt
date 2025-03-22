package com.example.cpdandroid

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cpdandroid.ui.theme.CPDAndroidTheme
import com.example.app.network.RetrofitClient
import com.example.cpdandroid.ui.navigation.AppNavigation
import com.example.cpdandroid.ui.screen.TestMainScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CPDAndroidTheme {
                AppNavigation()
            }
        }
    }
}

//@Composable
//fun MainScreen(navController: NavController) {
//    val scope = rememberCoroutineScope()
//    val context = LocalContext.current
//    var message by remember { mutableStateOf("Press the button") }
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = message)
//        Spacer(modifier = Modifier.height(20.dp))
//        Button(onClick = {
//            scope.launch {
//                try {
//                    // Retrofit API 호출 및 응답 처리
//                    val response = RetrofitClient.apiService.getHelloMessage()
//                    message = response.message  // 응답의 message를 상태에 설정
//                } catch (e: Exception) {
//                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }) {
//            Text("Fetch Data")
//        }
//
//        //뒤로 가는 버튼
//        Button(
//            onClick = { navController.popBackStack() },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text("Back to Home")
//        }
//    }
//}

//@Composable
//fun NavigationApp() {
//
//    //NavController 생성 (화면 전환 관리)
//    val navController = rememberNavController()
//
//    NavHost(navController = navController, startDestination = "home") {
//        composable("home") { HomeScreen(navController)} //홈화면 정하기
//        composable("details") { DetailScreen(navController)}
//        composable("get") { MainScreen(navController)}
//    }
//}


//@Composable
//fun HomeScreen(navController: NavController) {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center
//    ) {
//        Button(
//            onClick = { navController.navigate("details") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text("Go to Details")
//        }
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Button(
//            onClick = { navController.navigate("get") },
//            modifier =  Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text("Fetch API Data")
//        }
//    }
//}
//
//@Composable
//fun DetailScreen(navController: NavController) {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text("This is the Details Screen", modifier = Modifier.padding(16.dp))
//
//        Button(
//            onClick = { navController.popBackStack() },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text("Back to Home")
//        }
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    NavigationApp()
//}

//@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
//@Composable
//fun PreviewTestMainScreen() {
//    TestMainScreen()
//}



