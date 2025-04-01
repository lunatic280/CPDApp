package com.example.app.network

import com.example.cpdandroid.data.Blog
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

data class MessageResponse(val message: String)

interface ApiService {
    @GET("/api/hello")
    suspend fun getHelloMessage(): MessageResponse

    @POST("/api/create-blog")
    suspend fun createBlog(@Body blog: Blog): Blog

}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080"

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()) // ✅ Moshi 변환기 추가
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi)) // ✅ Moshi 변환기 적용
            .build()
            .create(ApiService::class.java)
    }
}
