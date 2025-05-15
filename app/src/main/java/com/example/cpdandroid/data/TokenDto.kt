package com.example.cpdandroid.data

import com.squareup.moshi.Json

data class TokenDto(
    @Json(name = "accessToken") val accessToken: String,
    @Json(name = "refreshToken") val refreshToken: String
)
