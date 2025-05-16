package com.example.cpdandroid.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cpdandroid.data.UserDto
import com.example.cpdandroid.data.DogDto
import com.example.cpdandroid.model.AuthViewModel
import com.example.cpdandroid.model.DogViewModel

// DogCreateScreen.kt (수정본)
@Composable
fun DogCreateScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    dogViewModel: DogViewModel
) {
    var dogName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }

    val userEmail = authViewModel.userEmail.value ?: ""
    val userName = authViewModel.userName.value ?: ""
    val error by dogViewModel.errorMessage.collectAsState()
    var showOwnerError by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("강아지 등록", style = MaterialTheme.typography.titleLarge)
        if (error != null) {
            Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
        }
        if (showOwnerError) {
            Text(
                text = "로그인 정보가 비어 있습니다. 다시 로그인해주세요.",
                color = MaterialTheme.colorScheme.error
            )
        }
        OutlinedTextField(
            value = dogName,
            onValueChange = { dogName = it },
            label = { Text("이름") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("나이") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("몸무게") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = breed,
            onValueChange = { breed = it },
            label = { Text("견종") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                // 로그인 정보가 비어 있으면 등록 시도 X
                if (userEmail.isBlank() || userName.isBlank()) {
                    showOwnerError = true
                    return@Button
                } else {
                    showOwnerError = false
                }
                val dogDto = DogDto(
                    dogName = dogName,
                    age = age.toIntOrNull() ?: 0,
                    weight = weight.toIntOrNull() ?: 0,
                    breed = breed,
                    owner = UserDto(name = userName, email = userEmail)
                )
                println("등록 요청 dogDto: $dogDto")
                dogViewModel.addDog(dogDto) {
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("등록하기")
        }
    }
}

