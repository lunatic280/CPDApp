package com.example.cpdandroid.ui.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.google.android.gms.location.*

@Composable
fun rememberLocationState(context: Context): State<Location?> {
    val locationState = remember { mutableStateOf<Location?>(null) }
    val fusedLocationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    DisposableEffect(key1 = true) {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationState.value = locationResult.lastLocation
            }
        }

        fun startLocationUpdates() {
            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 1000
            ).build()
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                try {
                    fusedLocationProviderClient.requestLocationUpdates(
                        locationRequest,
                        ContextCompat.getMainExecutor(context),
                        locationCallback
                    )
                } catch (e: SecurityException) {
                    // 권한 예외 처리
                    e.printStackTrace()
                }
            } else {
                // 권한이 없을 때 처리 (ex. 안내 메시지, requestPermission)
            }
        }

        startLocationUpdates()

        onDispose {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }
    return locationState
}
