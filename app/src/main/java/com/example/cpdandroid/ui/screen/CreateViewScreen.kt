package com.example.cpdandroid.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cpdandroid.model.BlogViewModel
import com.example.cpdandroid.ui.components.CustomBottomBar
import com.example.cpdandroid.ui.components.CustomTopBar

@Composable
fun CreateViewScreen(viewModel: BlogViewModel, navController: NavController) {

    val isPostSuccessful by viewModel.isPostSuccessful.collectAsState() // ✅ 상태 감지

    LaunchedEffect(isPostSuccessful) {
        if (isPostSuccessful) {
            navController.navigate("Home") { // ✅ 메인 화면으로 이동
                popUpTo("Home") { inclusive = true } // ✅ 이전 화면 삭제 (뒤로가기 방지)
            }
            viewModel.resetPostStatus() // ✅ 상태 초기화
        }
    }

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CustomTopBar(title = "상단 앱바")
        },
        bottomBar = { CustomBottomBar(title = "하단 앱 바 ") }
    ) {innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column {
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Button(onClick = { viewModel.createBlog(title, content) }) {
                Text("게시하기")
            }
            Button(onClick = { navController.popBackStack() }) {
                Text("뒤로가기")
            }
        }
    }

}




@Preview(showBackground = true)
@Composable
fun TestCreateViewScreen() {
    val navController = rememberNavController()

    CreateViewScreen(viewModel(), navController)
}