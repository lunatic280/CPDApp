package com.example.cpdandroid.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cpdandroid.data.DogDto
import com.example.cpdandroid.data.UserDto
import com.example.cpdandroid.model.DogUiState
import com.example.cpdandroid.model.DogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogCreateScreen(
    viewModel: DogViewModel,
    navController: NavController,
    user: UserDto,
    onSubmit: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    val uiState by viewModel.uiState

    Scaffold(

    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("이름") })
            OutlinedTextField(value = breed, onValueChange = { breed = it }, label = { Text("견종") })
            OutlinedTextField(
                value = age,
                onValueChange = { age = it.filter { c -> c.isDigit() } },
                label = { Text("나이") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (name.isNotBlank() && breed.isNotBlank() && age.isNotBlank()) {
                    viewModel.createDog(
                        DogDto(
                            id = null,
                            dogName = name,
                            breed = breed,
                            age = age.toInt(),
                            user = user
                        ),
                        onSuccess = onSubmit
                    )
                }
            }) {
                Text("등록")
            }
            when (uiState) {
                is DogUiState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(8.dp))
                is DogUiState.Error -> Text((uiState as DogUiState.Error).error, color = MaterialTheme.colorScheme.error)
                is DogUiState.Success -> Text((uiState as DogUiState.Success).message, color = MaterialTheme.colorScheme.primary)
                else -> {}
            }
        }
    }
}
