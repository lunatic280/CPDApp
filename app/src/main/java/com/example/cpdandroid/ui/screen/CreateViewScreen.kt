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
fun CreateViewScreen(
    viewModel: BlogViewModel,
    navController: NavController,
    authorEmail: String, // 이메일 추가
    authorName: String   // 작성자 이름 추가
) {

    val isPostSuccessful by viewModel.isPostSuccessful.collectAsState()

    LaunchedEffect(isPostSuccessful) {
        if (isPostSuccessful) {
            navController.navigate("Home") {
                popUpTo("Home") { inclusive = true }
            }
            viewModel.resetPostStatus()
        }
    }

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Scaffold(
        topBar = { CustomTopBar(title = "상단 앱바") },
        bottomBar = { CustomBottomBar(title = "하단 앱 바 ") }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(onClick = { viewModel.createBlog(title, content, authorEmail, authorName) }) {
                Text("게시하기")
            }
            Button(onClick = { navController.popBackStack() }) {
                Text("뒤로가기")
            }
        }
    }
}





//@Preview(showBackground = true)
//@Composable
//fun TestCreateViewScreen() {
//    val navController = rememberNavController()
//
//    CreateViewScreen(viewModel(), navController)
//}