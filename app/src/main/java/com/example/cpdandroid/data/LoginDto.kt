package com.example.cpdandroid.data

import com.squareup.moshi.Json

data class LoginDto(
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String
)
