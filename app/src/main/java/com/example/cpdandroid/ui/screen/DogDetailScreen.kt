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
import com.example.cpdandroid.model.DogUiState
import com.example.cpdandroid.model.DogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogDetailScreen(
    dogId: Long,
    viewModel: DogViewModel,
    navController: NavController,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val dog by viewModel.dogDetail
    val uiState by viewModel.uiState

    LaunchedEffect(dogId) {
        viewModel.loadDogDetail(dogId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("강아지 상세") },
                navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "뒤로") } }
            )
        },
        floatingActionButton = {
            Row {
                FloatingActionButton(onClick = onEdit) { Text("수정") }
                Spacer(modifier = Modifier.width(8.dp))
                FloatingActionButton(onClick = {
                    dog?.id?.let { viewModel.deleteDog(it, onSuccess = onDelete) }
                }) { Text("삭제") }
            }
        }
    ) { padding ->
        when (uiState) {
            is DogUiState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            is DogUiState.Error -> Text(
                text = (uiState as DogUiState.Error).error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
            else -> {
                dog?.let {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("이름: ${it.dogName}", style = MaterialTheme.typography.headlineSmall)
                        Text("견종: ${it.breed}")
                        Text("나이: ${it.age}")
                        Text("주인 이메일: ${it.user}")
                    }
                }
            }
        }
    }
}
