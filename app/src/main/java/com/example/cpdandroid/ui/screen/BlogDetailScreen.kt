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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cpdandroid.model.AuthViewModel
import com.example.cpdandroid.model.BlogViewModel
import com.example.cpdandroid.ui.components.CustomBottomBar
import com.example.cpdandroid.ui.components.CustomTopBar

@Composable
fun BlogDetailScreen(
    id: Long,
    blogViewModel: BlogViewModel,
    authViewModel: AuthViewModel,
    navController: NavController
) {
    val blog by blogViewModel.selectedBlog.collectAsState()
    LaunchedEffect(id) {
        blogViewModel.getBlog(id)
    }

    val userEmail by authViewModel.userEmail
    val canEdit = remember(blog, userEmail) {
        blog?.author?.email == userEmail
    }

    Scaffold(
        topBar = { CustomTopBar(title = "블로그 상세") },
        bottomBar = { CustomBottomBar(title = "하단 앱 바") }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            blog?.let {
                Text("📌 제목: ${it.title}", Modifier.padding(16.dp))
                Text("📝 내용: ${it.content}", Modifier.padding(16.dp))
            } ?: Text("블로그 데이터를 불러오는 중...", Modifier.padding(16.dp))

            Spacer(Modifier.weight(1f))

            Button(
                onClick = { navController.navigate("BlogDetail/update/$id") },
                enabled = canEdit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(if (canEdit) "수정하기" else "수정 권한 없음")
            }

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("뒤로가기")
            }

            Button(
                onClick = { navController.navigate("BlogDetail/delete/$id") },
                enabled = canEdit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(if (canEdit) "삭제" else "삭제 권한 없음")
            }
        }
    }
}


