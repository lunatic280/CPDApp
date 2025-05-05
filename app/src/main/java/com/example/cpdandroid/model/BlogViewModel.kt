package com.example.cpdandroid.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.network.RetrofitClient
import com.example.cpdandroid.data.Blog
import com.example.cpdandroid.data.LoginDto
import com.example.cpdandroid.data.UserDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BlogViewModel(
    private val authViewModel: AuthViewModel? = null
) : ViewModel() {

    private val _blogs = MutableStateFlow<List<Blog>>(emptyList())
    val blogs: StateFlow<List<Blog>> = _blogs

    private val _selectedBlog = MutableStateFlow<Blog?>(null)  // ✅ 단일 블로그 상태 추가
    val selectedBlog: StateFlow<Blog?> = _selectedBlog

    private val _isPostSuccessful = MutableStateFlow(false) // ✅ 게시 성공 여부를 저장할 상태
    val isPostSuccessful: StateFlow<Boolean> = _isPostSuccessful

    fun createBlog(title: String, content: String, authorEmail: String, authorName: String) {
        viewModelScope.launch {
            try {
                val author = UserDto(name = authorName, email = authorEmail)
                val blog = Blog(title = title, content = content, author = author)
                val response = RetrofitClient.api.createBlog(blog)
                println("Create Blog Response: $response")
                _isPostSuccessful.value = response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                _isPostSuccessful.value = false
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

    fun updateBlog(id: Long, title: String, content: String) {
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