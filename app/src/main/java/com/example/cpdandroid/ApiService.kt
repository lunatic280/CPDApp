package com.example.app.network

import com.example.cpdandroid.data.Blog
import com.example.cpdandroid.data.DogDto
import com.example.cpdandroid.data.LoginDto
import com.example.cpdandroid.data.UserDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.net.CookieManager
import java.net.CookiePolicy

data class MessageResponse(val message: String)

interface ApiService {
    @GET("/api/hello")
    suspend fun getHelloMessage(): MessageResponse

    @GET("api/")
    suspend fun getAllBlog(): List<Blog>

    @GET("api/{id}")
    suspend fun getBlog(@Path("id") id: Long): Response<Blog>

    @POST("/api/create-blog")
    suspend fun createBlog(@Body blog: Blog): Response<Blog>

    @PUT("/api/update/{id}")
    suspend fun updateBlog(@Path("id") id: Long, @Body blog: Blog): Response<Blog>

    @DELETE("/api/delete/{id}")
    suspend fun deleteBlog(@Path("id") id: Long)

    @POST("/api/auth/signup")
    suspend fun signup(@Body userDto: UserDto): Response<MessageResponse>

    @POST("/api/auth/login")
    suspend fun login(@Body loginDto: LoginDto): Response<MessageResponse>

    @GET("/dog")
    suspend fun getMyDogs(): List<DogDto>

    @POST("/dog/create-dog")
    suspend fun createDog(@Body dog: DogDto): Response<DogDto>

    @DELETE("/dog/{id}")
    suspend fun deleteDog(@Path("id") id: Long)

}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080"

//    // CookieManager to accept and persist session cookies
//    private val cookieManager = CookieManager().apply {
//        setCookiePolicy(CookiePolicy.ACCEPT_ALL)
//    }

    // Logging interceptor for debugging headers and body
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // OkHttpClient with enhanced cookie handling
    private val cookieManager = CookieManager().apply {
        setCookiePolicy(CookiePolicy.ACCEPT_ALL)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .cookieJar(JavaNetCookieJar(cookieManager))
        .addInterceptor(loggingInterceptor)  // logging interceptor를 networkInterceptor가 아닌 일반 interceptor로 변경 권장
        .build()

    // Moshi instance with Kotlin adapter for data class support
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiService::class.java)
    }
}
