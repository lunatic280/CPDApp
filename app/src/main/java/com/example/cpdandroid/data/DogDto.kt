package com.example.cpdandroid.data

import com.squareup.moshi.Json

data class DogDto(
    @Json(name = "id") val id: Long? = null,
    @Json(name = "dogName") val dogName: String,
    @Json(name = "age") val age: Int,
    @Json(name = "weight") val weight: Int,
    @Json(name = "breed") val breed: String,
    @Json(name = "owner") val owner: UserDto
)
