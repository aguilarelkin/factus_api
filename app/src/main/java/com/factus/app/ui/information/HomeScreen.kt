package com.factus.app.ui.information

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.factus.app.domain.models.invoice.FactureItem
import com.factus.app.domain.state.LoginResult
import com.factus.app.ui.navigation.RouteFactus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel, navController: NavHostController, modifier: Modifier) {
    val searchState by homeViewModel.factureState.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            SearchBar { querys ->
                homeViewModel.getInvoice(querys)
            }
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = { navController.navigate(RouteFactus.Facture.route) }) {
            Icon(Icons.Default.ShoppingCart, contentDescription = "Facturar")
        }
    }) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            when (searchState) {
                is LoginResult.Error -> {
                    val errorMessage = (searchState as LoginResult.Error<List<FactureItem>>).message
                    Text(
                        text = "Error: $errorMessage", color = Color.Red
                    )
                }

                is LoginResult.Loading -> {
                    val data = (searchState as LoginResult.Loading<*>).isLoading
                    if (data) {
                        CircularProgressIndicator()
                    }
                }

                is LoginResult.Success -> {
                    val data = (searchState as LoginResult.Success<List<FactureItem>>).data
                    Text(text = "Login exitoso: ${data}")
                }
            }

        }
    }
}

@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var query by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp)
            .border(2.dp, Color.Blue, RoundedCornerShape(8.dp))
            .background(Color.White, RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Buscar...", color = Color.Gray) },
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            singleLine = true
        )

        IconButton(
            onClick = { onSearch(query) },
            modifier = Modifier
                .background(Color.Blue, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = Color.White
            )
        }
    }
}
