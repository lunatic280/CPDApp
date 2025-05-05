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

class AuthViewModel : ViewModel() {
    // 로그인 상태
    private val _authState = mutableStateOf<AuthState>(AuthState.Idle)
    val authState: State<AuthState> = _authState

    // 실시간 사용자 정보 저장
    private val _userEmail = mutableStateOf<String?>(null)
    val userEmail: State<String?> = _userEmail

    private val _userName = mutableStateOf<String?>(null)
    val userName: State<String?> = _userName

    fun signup(name: String, email: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val userDto = UserDto(name = name, email = email, password = password)
                val response = RetrofitClient.api.signup(userDto)

                if (response.isSuccessful) {
                    val msg = response.body()?.message ?: "회원가입 성공"
                    _authState.value = AuthState.Success(msg)
                    // 가입과 동시에 정보 저장
                    _userEmail.value = email
                    _userName.value = name
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
                    val msg = response.body()?.message ?: "로그인 성공"
                    _authState.value = AuthState.Success(msg)
                    // 로그인 성공 시 사용자 정보 저장
                    _userEmail.value = email
                    _userName.value = email.substringBefore("@") // 예시: 이메일 앞부분 사용
                } else {
                    val err = response.errorBody()?.string() ?: "로그인 실패"
                    _authState.value = AuthState.Error("로그인 실패: $err")
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