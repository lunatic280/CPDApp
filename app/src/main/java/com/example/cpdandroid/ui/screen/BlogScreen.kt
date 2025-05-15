package com.example.cpdandroid.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cpdandroid.model.BlogViewModel
import com.example.cpdandroid.ui.components.CustomBottomBar
import com.example.cpdandroid.ui.components.CustomTopBar
import com.example.cpdandroid.ui.navigation.AppNavigation
import com.example.cpdandroid.ui.navigation.Screen

@Composable
fun BlogScreen(viewModel: BlogViewModel, navController: NavController) {

    val blogs by viewModel.blogs.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllBlog() // ✅ 화면 진입 시 API 호출
    }
    
    Scaffold(
        topBar = {
            CustomTopBar(title = "상단 앱바")
        },
        bottomBar = { CustomBottomBar(title = "하단 앱 바 ") }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Text("블로그 글 넣기")
            Button(
                onClick = { navController.popBackStack() }
            ) { Text("뒤로가기") }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            ) {
                items(blogs) { blog ->
                    Button(
                        onClick = { navController.navigate("BlogDetail/${blog.id}") }, // ✅ 클릭하면 BlogDetailScreen 이동
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "📌 제목: ${blog.title}\n📝 내용: ${blog.content}")
                    }
                }
            }
        }

    }


}

@Preview(showBackground = true)
@Composable
fun PreviewBlogScreen() {
    val navController = rememberNavController()
    BlogScreen(BlogViewModel(), navController)
}