package com.example.cpdandroid.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.network.RetrofitClient
import com.example.cpdandroid.data.Blog
import kotlinx.coroutines.launch

class BlogViewModel : ViewModel() {

    fun createBlog(title: String, content: String) {
        viewModelScope.launch {
            try {
                val newBlog = Blog(title = title, content = content)
                val response = RetrofitClient.api.createBlog(newBlog)
                println("Created Blog: $response")
            } catch (e: Exception) {
                e.printStackTrace() // ✅ 예외 스택 트레이스 출력
                println("Error: ${e.localizedMessage}")
            }
        }
    }

}