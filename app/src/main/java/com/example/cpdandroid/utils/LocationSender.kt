package com.example.cpdandroid.utils

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.app.network.ApiService
import com.example.cpdandroid.data.LocationDto
import com.example.cpdandroid.ui.components.rememberLocationState
import kotlinx.coroutines.delay
import java.time.ZonedDateTime

@Composable
fun LocationSender(api: ApiService) {
    val context = LocalContext.current
    val location by rememberLocationState(context)
    var isSending by remember { mutableStateOf(true) }

    LaunchedEffect(isSending) {
        if (isSending) {
            while (true) {
                location?.let {
                    val data = LocationDto(
                        latitude = it.latitude,
                        longitude = it.longitude,
                        timestamp = ZonedDateTime.now().toString()
                    )
                    try {
                        api.sendLocation(data)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                delay(5000L)
            }
        }
    }
}
