package com.example.cpdandroid.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cpdandroid.ui.components.CustomBottomBar
import com.example.cpdandroid.ui.components.CustomTopBar
import com.example.cpdandroid.ui.navigation.AppNavigation

@Preview(showBackground = true)
@Composable
fun PreviewTestMainScreen() {
    AppNavigation()
}




@Composable
fun TestMainScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CustomTopBar(title = "상단 앱바")
        },
        bottomBar = { CustomBottomBar(title = "하단 앱 바 ") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "중간에 나올 것들"
            )
            Button(
                onClick = { navController.navigate("Blog") },
                modifier =  Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) { Text("블로그 화면으로 이동하기") }

            Button(
                onClick =  { navController.navigate("Counter") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) { Text("카운터 화면으로 이동") }

            Button(
                onClick = { navController.navigate("CreateView") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) { Text("블로글 생성하기") }
        }
    }
}