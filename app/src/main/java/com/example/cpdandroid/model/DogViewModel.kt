package com.example.cpdandroid.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.network.RetrofitClient
import com.example.cpdandroid.data.DogDto
import kotlinx.coroutines.launch
import androidx.compose.runtime.State

sealed class DogUiState {
    object Idle : DogUiState()
    object Loading : DogUiState()
    data class Success(val message: String) : DogUiState()
    data class Error(val error: String) : DogUiState()
}

class DogViewModel : ViewModel() {
    private val api = RetrofitClient.api

    private val _dogs = mutableStateOf<List<DogDto>>(emptyList())
    val dogs: State<List<DogDto>> = _dogs

    private val _dogDetail = mutableStateOf<DogDto?>(null)
    val dogDetail: State<DogDto?> = _dogDetail

    private val _uiState = mutableStateOf<DogUiState>(DogUiState.Idle)
    val uiState: State<DogUiState> = _uiState

    fun loadDogs() {
        _uiState.value = DogUiState.Loading
        viewModelScope.launch {
            try {
                val res = api.getUserDog()
                if (res.isSuccessful) {
                    _dogs.value = res.body()?.filterNotNull() ?: emptyList()
                    _uiState.value = DogUiState.Idle
                } else {
                    _uiState.value = DogUiState.Error("목록 조회 실패: ${res.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = DogUiState.Error("네트워크 오류: ${e.localizedMessage}")
            }
        }
    }

    fun loadDogDetail(id: Long) {
        _uiState.value = DogUiState.Loading
        viewModelScope.launch {
            try {
                val res = api.getDog(id)
                if (res.isSuccessful) {
                    _dogDetail.value = res.body()
                    _uiState.value = DogUiState.Idle
                } else {
                    _uiState.value = DogUiState.Error("상세 조회 실패: ${res.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = DogUiState.Error("네트워크 오류: ${e.localizedMessage}")
            }
        }
    }

    fun createDog(dogDto: DogDto, onSuccess: () -> Unit = {}) {
        _uiState.value = DogUiState.Loading
        viewModelScope.launch {
            try {
                val res = api.registrationDog(dogDto)
                if (res.isSuccessful) {
                    _uiState.value = DogUiState.Success("강아지 등록 성공")
                    loadDogs()
                    onSuccess()
                } else {
                    _uiState.value = DogUiState.Error("등록 실패: ${res.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = DogUiState.Error("네트워크 오류: ${e.localizedMessage}")
            }
        }
    }

    fun deleteDog(id: Long, onSuccess: () -> Unit = {}) {
        _uiState.value = DogUiState.Loading
        viewModelScope.launch {
            try {
                val res = api.deleteDog(id)
                if (res.isSuccessful) {
                    _uiState.value = DogUiState.Success("삭제 성공")
                    loadDogs()
                    onSuccess()
                } else {
                    _uiState.value = DogUiState.Error("삭제 실패: ${res.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = DogUiState.Error("네트워크 오류: ${e.localizedMessage}")
            }
        }
    }

    fun resetUiState() {
        _uiState.value = DogUiState.Idle
    }
}