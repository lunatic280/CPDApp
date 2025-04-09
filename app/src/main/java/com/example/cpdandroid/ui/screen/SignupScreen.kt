package com.example.cpdandroid.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cpdandroid.model.AuthState
import com.example.cpdandroid.model.AuthViewModel
import com.example.cpdandroid.ui.components.CustomBottomBar
import com.example.cpdandroid.ui.components.CustomTopBar
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*


@Composable
fun SignupScreen(viewModel: AuthViewModel, navController: NavController,onSignupSuccess: () -> Unit) {

    val authState by viewModel.authState

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CustomTopBar(title = "상단 앱바")
        },
        bottomBar = { CustomBottomBar(title = "하단 앱 바 ") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center
        ) {
            Text("회원가입", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("이름") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("이메일") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("비밀번호") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.signup(name, email, password) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("회원가입")
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("뒤로가기")
            }

            when (authState) {
                is AuthState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
                is AuthState.Error -> {
                    Text(
                        text = (authState as AuthState.Error).error,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                is AuthState.Success -> {
                    onSignupSuccess()
                    viewModel.resetState()
                }
                else -> { /* Idle 상태 */ }
            }
        }
    }

}