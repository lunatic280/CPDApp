package com.example.cpdandroid.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Blog(
    @Json(name = "id") val id: Long? = null,
    @Json(name = "title") val title: String = "제목 없음",
    @Json(name = "content") val content: String = "내용 없음"
)
