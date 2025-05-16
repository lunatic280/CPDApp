package com.example.cpdandroid.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.network.RetrofitClient
import com.example.cpdandroid.data.DogDto
import com.example.cpdandroid.data.UserDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DogViewModel : ViewModel() {
    private val _dogs = MutableStateFlow<List<DogDto>>(emptyList())
    val dogs: StateFlow<List<DogDto>> = _dogs

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchDogs() {
        viewModelScope.launch {
            try {
                val result = RetrofitClient.api.getMyDogs()
                _dogs.value = result
            } catch (e: Exception) {
                _errorMessage.value = "강아지 목록 불러오기 실패: ${e.localizedMessage}"
            }
        }
    }

    fun addDog(dogDto: DogDto, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.createDog(dogDto)
                if (response.isSuccessful) {
                    fetchDogs()
                    onSuccess()
                } else {
                    _errorMessage.value = "등록 실패: ${response.code()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "등록 실패: ${e.localizedMessage}"
            }
        }
    }

    fun deleteDog(id: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                RetrofitClient.api.deleteDog(id)
                fetchDogs()
                onSuccess()
            } catch (e: Exception) {
                _errorMessage.value = "삭제 실패: ${e.localizedMessage}"
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
