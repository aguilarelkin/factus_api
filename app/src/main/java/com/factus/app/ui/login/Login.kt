package com.factus.app.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.factus.app.domain.models.Token
import com.factus.app.domain.state.LoginResult
import com.factus.app.ui.navigation.RouteFactus

@Composable
fun Login(loginViewModel: LoginViewModel, navController: NavHostController, modifier: Modifier) {
    val uiState by loginViewModel.loginState.collectAsState()
    val uiStateLogin by loginViewModel.loginUser.collectAsState()
    var enabled = true

    when (uiStateLogin) {
        true -> {
            enabled = false
            LaunchedEffect(Unit) {
                loginViewModel.refreshTokenApi(
                    "refresh_token",
                    "9de80182-9fc7-426f-85f0-8de4af2064a7",
                    "fbvKW8z5QtjWlaCtrIgWq2ti3XRjMcJptI4rU32U",
                )
            }
        }

        false -> {
            enabled = true
        }
    }

    /*LaunchedEffect(Unit) {
                        loginViewModel.loginFactusApi(
                            "password",
                            "9de80182-9fc7-426f-85f0-8de4af2064a7",
                            "fbvKW8z5QtjWlaCtrIgWq2ti3XRjMcJptI4rU32U",
                            "sandbox@factus.com.co",
                            "sandbox2024%"
                        )
                    }*/
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is LoginResult.Loading -> {
                val data = (uiState as LoginResult.Loading<*>).isLoading
                enabled = !data
                if (data) {
                    CircularProgressIndicator()
                }
            }

            is LoginResult.Success -> {
                //val data = (uiState as LoginResult.Success<Token>).data
                // Text(text = "Login exitoso: ${data?.access_token}")
                navController.navigate(RouteFactus.Home.route) {
                    popUpTo(RouteFactus.Login.route) {
                        inclusive = true
                    }
                }
            }

            is LoginResult.Error -> {
                val errorMessage = (uiState as LoginResult.Error<Token>).message
                Text(
                    text = "Error: $errorMessage", color = Color.Red
                )
            }
        }

        Button(onClick = {
            loginViewModel.loginFactusApi(
                "password",
                "9de80182-9fc7-426f-85f0-8de4af2064a7",
                "fbvKW8z5QtjWlaCtrIgWq2ti3XRjMcJptI4rU32U",
                "sandbox@factus.com.co",
                "sandbox2024%"
            )

        }, enabled = enabled) {
            Text("Iniciar Sesión")
        }
    }
}