package com.factus.app.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.factus.app.R
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
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_lock),
            contentDescription = "Login",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 40.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        when (uiState) {
            is LoginResult.Loading -> {
                val data = (uiState as LoginResult.Loading<*>).isLoading
                enabled = !data
                if (data) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        strokeWidth = 3.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
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
                ErrorMessage(message = errorMessage)
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                loginViewModel.loginFactusApi(
                    "password",
                    "9de80182-9fc7-426f-85f0-8de4af2064a7",
                    "fbvKW8z5QtjWlaCtrIgWq2ti3XRjMcJptI4rU32U",
                    "sandbox@factus.com.co",
                    "sandbox2024%"
                )

            },
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            if ((uiState as? LoginResult.Loading<*>)?.isLoading == true) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    text = "Iniciar Sesión",
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

        }
    }
}

@Composable
fun ErrorMessage(message: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(MaterialTheme.colorScheme.errorContainer, RoundedCornerShape(8.dp))
            .padding(12.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = message ?: "Error desconocido",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}