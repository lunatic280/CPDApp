package com.example.app.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import com.google.gson.GsonBuilder

// 서버에서 반환하는 객체를 JSON 형식으로 처리
data class MessageResponse(val message: String)

interface ApiService {
    @GET("/api/hello")
    suspend fun getHelloMessage(): MessageResponse  // JSON 객체를 처리
}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080"

    val apiService: ApiService by lazy {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}
