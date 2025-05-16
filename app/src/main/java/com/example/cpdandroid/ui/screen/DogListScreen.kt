package com.example.cpdandroid.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cpdandroid.data.DogDto
import com.example.cpdandroid.model.AuthViewModel
import com.example.cpdandroid.model.DogViewModel

@Composable
fun DogListScreen(
    navController: NavController,
    dogViewModel: DogViewModel,
    authViewModel: AuthViewModel
) {
    val dogs by dogViewModel.dogs.collectAsState()
    val error by dogViewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        dogViewModel.fetchDogs()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("내 강아지 목록", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        if (error != null) {
            Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
        }
        LazyColumn {
            items(dogs.size) { idx ->
                val dog = dogs[idx]
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)) {
                    Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text("이름: ${dog.dogName}")
                            Text("나이: ${dog.age}")
                            Text("몸무게: ${dog.weight}")
                            Text("견종: ${dog.breed}")
                        }
                        Button(
                            onClick = { dogViewModel.deleteDog(dog.id ?: return@Button) { /* 삭제 후 처리 */ } },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                        ) { Text("삭제") }
                    }
                }
            }
        }
        Spacer(Modifier.height(24.dp))
        Button(onClick = { navController.navigate("DogCreate") }, modifier = Modifier.fillMaxWidth()) {
            Text("강아지 등록하기")
        }
    }
}
