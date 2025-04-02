package com.example.cpdandroid.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.network.RetrofitClient
import com.example.cpdandroid.data.Blog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BlogViewModel : ViewModel() {

    private val _blogs = MutableStateFlow<List<Blog>>(emptyList())
    val blogs: StateFlow<List<Blog>> = _blogs

    private val _selectedBlog = MutableStateFlow<Blog?>(null)  // ✅ 단일 블로그 상태 추가
    val selectedBlog: StateFlow<Blog?> = _selectedBlog

    private val _isPostSuccessful = MutableStateFlow(false) // ✅ 게시 성공 여부를 저장할 상태
    val isPostSuccessful: StateFlow<Boolean> = _isPostSuccessful

    fun createBlog(title: String, content: String) {
        viewModelScope.launch {
            try {
                val newBlog = Blog(title = title, content = content)
                val response = RetrofitClient.api.createBlog(newBlog)
                println("Created Blog: $response")

                if (response.isSuccessful) {
                    _isPostSuccessful.value = true // ✅ 성공 상태 업데이트
                }
            } catch (e: Exception) {
                e.printStackTrace() // ✅ 예외 스택 트레이스 출력
                println("Error: ${e.localizedMessage}")
            }
        }
    }

    fun getAllBlog() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getAllBlog()
                _blogs.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error fetching blogs: ${e.localizedMessage}")
            }
        }
    }

    fun getBlog(id: Long) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getBlog(id)

                if (response.isSuccessful) {
                    response.body()?.let { blog ->
                        _selectedBlog.value = blog
                    }
                } else {
                    println("Error: ${response.errorBody()?.string()}")
                }

            } catch (e: Exception) {
                e.printStackTrace()
                println("Error: ${e.localizedMessage}")
            }

        }
    }

    fun updateBlog(id: String, title: String, content: String) {
        viewModelScope.launch {
            try {

            } catch (e: Exception) {
                e.printStackTrace()
                println("Error: ${e.localizedMessage}")
            }
        }
    }

    fun resetPostStatus() {
        _isPostSuccessful.value = false // ✅ 상태 초기화
    }

}