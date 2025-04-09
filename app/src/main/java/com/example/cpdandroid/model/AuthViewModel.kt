package com.example.cpdandroid.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.network.RetrofitClient
import com.example.cpdandroid.data.LoginDto
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import com.example.cpdandroid.data.UserDto
import kotlin.math.log

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val error: String) : AuthState()
}

class AuthViewModel: ViewModel() {

    private val _authState = mutableStateOf<AuthState>(AuthState.Idle)
    val authState: State<AuthState> = _authState


    fun signup(name: String, email: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val userDto = UserDto(name = name, email = email, password = password)
                val response = RetrofitClient.api.signup(userDto)

                if (response.isSuccessful) {
                    val message = response.body()?.message ?: "회원가입 성공"
                    _authState.value = AuthState.Success(message)
                } else {
                    _authState.value = AuthState.Error("회원가입 실패: ${response.code()}")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("회원가입 실패: ${e.localizedMessage}")
            }
        }
    }

    fun login(email: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val loginDto = LoginDto(email, password)
                val response = RetrofitClient.api.login(loginDto)
                if (response.isSuccessful) {
                    val message = response.body()?.message ?: "로그인 성공"
                    _authState.value = AuthState.Success(message)
                } else {
                    val error = response.errorBody()?.string() ?: "로그인 실패"
                    _authState.value = AuthState.Error("로그인 실패: $error")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("로그인 실패: ${e.localizedMessage}")
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}