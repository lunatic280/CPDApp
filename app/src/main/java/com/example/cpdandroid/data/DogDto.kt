package com.example.cpdandroid.data

import com.squareup.moshi.Json

data class DogDto(
    @Json(name = "id") val id: Long?,
    @Json(name = "dogName") val dogName: String,
    @Json(name = "breed") val breed: String,
    @Json(name = "age") val age: Int,
    @Json(name = "user") val user: UserDto
)
