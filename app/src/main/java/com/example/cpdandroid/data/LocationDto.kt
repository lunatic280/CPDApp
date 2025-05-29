package com.example.cpdandroid.data

import android.location.Location
import com.squareup.moshi.Json


data class LocationDto(
    @Json(name = "timestamp") val timestamp: String,
    @Json(name = "latitude") val latitude: Double,
    @Json(name = "longitude") val longitude: Double
)
