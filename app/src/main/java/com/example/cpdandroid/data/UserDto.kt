package com.example.cpdandroid.data

import com.squareup.moshi.Json

data class UserDto(
    @Json(name = "name") val name: String,
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String
)
