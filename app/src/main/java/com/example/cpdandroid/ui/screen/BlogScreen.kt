package com.example.cpdandroid.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cpdandroid.ui.components.CustomBottomBar
import com.example.cpdandroid.ui.components.CustomTopBar
import com.example.cpdandroid.ui.navigation.AppNavigation
import com.example.cpdandroid.ui.navigation.Screen

@Composable
fun BlogScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CustomTopBar(title = "상단 앱바")
        },
        bottomBar = { CustomBottomBar(title = "하단 앱 바 ") }
    ) {innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text("블로그 글 넣기")
            Button(
                onClick = { navController.popBackStack() }
            ) { Text("뒤로가기") }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun PreviewBlogScreen() {
    val navController = rememberNavController()
    BlogScreen(navController)
}