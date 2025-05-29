package com.example.cpdandroid.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import com.example.cpdandroid.ui.components.rememberLocationState
import com.example.cpdandroid.utils.LocationSender
import com.example.app.network.ApiService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationTrackerScreen(api: ApiService) {   // 반드시 api를 넘겨받게 수정!
    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(false) }

    // 런타임 권한 요청 런처
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionGranted = granted
    }

    // 최초 실행 또는 recomposition 시 권한 확인 및 요청
    LaunchedEffect(Unit) {
        val check = context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        permissionGranted = check
        if (!check) {
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    if (permissionGranted) {
        // --- 서버 전송 Composable 조건부로 포함 ---
        LocationSender(api = api)

        val location by rememberLocationState(context)

        Scaffold(
            topBar = { TopAppBar(title = { Text("실시간 위치 추적기") }) }
        ) { paddingValues ->
            Surface(modifier = Modifier.padding(paddingValues)) {
                if (location != null) {
                    Text(text = "위도: ${location!!.latitude}\n경도: ${location!!.longitude}")
                } else {
                    Text("위치 정보를 가져오는 중...")
                }
            }
        }
    } else {
        Scaffold(
            topBar = { TopAppBar(title = { Text("실시간 위치 추적기") }) }
        ) { paddingValues ->
            Surface(modifier = Modifier.padding(paddingValues)) {
                Text("앱의 위치 기능 사용을 위해\n위치 권한을 허용해 주세요.")
            }
        }
    }
}
