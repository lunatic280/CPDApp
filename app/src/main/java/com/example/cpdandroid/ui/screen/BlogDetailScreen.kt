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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cpdandroid.model.BlogViewModel
import com.example.cpdandroid.ui.components.CustomBottomBar
import com.example.cpdandroid.ui.components.CustomTopBar

@Composable
fun BlogDetailScreen(id: Long, viewModel: BlogViewModel, navController: NavController) {
    val blog by viewModel.selectedBlog.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getBlog(id)
    }

    Scaffold(
        topBar = {
            CustomTopBar(title = "블로그 상세")
        },
        bottomBar = { CustomBottomBar(title = "하단 앱 바") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            blog?.let { // ✅ blog가 null이 아닐 때만 UI를 표시
                LazyColumn {
                    item {
                        Text(text = "📌 제목: ${it.title}", modifier = Modifier.padding(16.dp))
                        Text(text = "📝 내용: ${it.content}", modifier = Modifier.padding(16.dp))
                    }
                }
            } ?: run {
                Text(text = "블로그 데이터를 불러오는 중...", modifier = Modifier.padding(16.dp))
            }

            Spacer(modifier = Modifier.weight(1f)) // 버튼을 하단으로 밀기 위한 Spacer
            Button(
                onClick = { navController.navigate() },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text("수정하기")
            }
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) { Text("뒤로가기") }
        }
    }
}