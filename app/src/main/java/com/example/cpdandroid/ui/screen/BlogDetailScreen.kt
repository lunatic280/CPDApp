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
    // 1) blog 데이터 로드
    val blog by blogViewModel.selectedBlog.collectAsState()
    LaunchedEffect(id) {
        blogViewModel.getBlog(id)
    }

    // 2) AuthViewModel 에서 userEmail 상태 직접 구독
    //    AuthViewModel.kt 에 정의된 프로퍼티 이름입니다 :contentReference[oaicite:0]{index=0}:contentReference[oaicite:1]{index=1}
    val userEmail by authViewModel.userEmail

    // 3) 수정 권한 플래그 계산
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

            // 4) 수정 버튼: canEdit 에 따라 활성화/비활성화
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
        }
    }
}

