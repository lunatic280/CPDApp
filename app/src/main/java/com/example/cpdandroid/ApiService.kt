package com.example.app.network

import com.example.cpdandroid.data.Blog
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT

data class MessageResponse(val message: String)

interface ApiService {
    @GET("/api/hello")
    suspend fun getHelloMessage(): MessageResponse

    @GET("/api/")
    suspend fun getAllBlog(): List<Blog>

    @GET("/api/{id}")
    suspend fun getBlog(): Blog

    @POST("/api/create-blog")
    suspend fun createBlog(@Body blog: Blog): Response<Blog>

    @PUT("/api/update/{id}")
    suspend fun updateBlog(): Blog

    @DELETE("/api/{id}")
    suspend fun deleteBlog(): Unit




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
