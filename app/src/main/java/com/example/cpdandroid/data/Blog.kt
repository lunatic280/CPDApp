package com.example.cpdandroid.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Blog(
    @Json(name = "title") val title: String,
    @Json(name = "content") val content: String
)
