package com.example.cpdandroid.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cpdandroid.data.DogDto
import com.example.cpdandroid.model.DogUiState
import com.example.cpdandroid.model.DogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogListScreen(
    viewModel: DogViewModel,
    navController: NavController,
    onDogClick: (Long) -> Unit,
    onAddClick: () -> Unit
) {
    val dogs by viewModel.dogs
    val uiState by viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadDogs()
    }
    Button(
        onClick = { navController.navigate("DogCreate") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) { Text("개 생성") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("내 강아지 목록") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }
    ) { padding ->
        when (uiState) {
            is DogUiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
            is DogUiState.Error -> Text(
                text = (uiState as DogUiState.Error).error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
            else -> {
                LazyColumn(modifier = Modifier.padding(padding)) {
                    items(dogs.size) { idx ->
                        val dog = dogs[idx]
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { onDogClick(dog.id!!) }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("이름: ${dog.dogName}")
                                Text("견종: ${dog.breed}")
                                Text("나이: ${dog.age}")
                            }
                            Button(
                                onClick = { navController.navigate("DogCreate") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) { Text("개 생성") }
                        }
                    }
                }
            }
        }
    }
}
