package com.factus.app.ui.information

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.factus.app.domain.models.invoice.FactureItem
import com.factus.app.domain.state.LoginResult
import com.factus.app.ui.navigation.RouteFactus
import kotlinx.coroutines.flow.collectLatest
import java.io.File

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
                    ListInvoice(data, homeViewModel)

                }
            }

        }
    }
}

@Composable
fun ListInvoice(data: List<FactureItem>?, homeViewModel: HomeViewModel) {
    val downloadInvoive by homeViewModel.downloadState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        homeViewModel.toastMessage.collectLatest { mensaje ->
            Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
        }
    }
    LaunchedEffect(key1 = true) {
        homeViewModel.pdfDownloadedEvent.collectLatest { (id, file) ->
            Toast.makeText(context, "PDF descargado: ${file.name}", Toast.LENGTH_SHORT).show()
            homeViewModel.openPdf(context, file)
        }
    }
    when (downloadInvoive) {
        is LoginResult.Error -> {
            val message = (downloadInvoive as LoginResult.Error<File>).message
            Toast.makeText(context, "Error: $message", Toast.LENGTH_LONG).show()
        }

        is LoginResult.Loading -> {
            val data = (downloadInvoive as LoginResult.Loading<*>).isLoading
            if (data) {
                CircularProgressIndicator()
            }
        }

        is LoginResult.Success -> {}
    }
    LazyColumn {
        if (!data.isNullOrEmpty()) {
            itemsIndexed(data) { index, item ->
                ItemInvoice(item, homeViewModel)
            }
        } else {
            item {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay datos disponibles",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }

}

@Composable
fun ItemInvoice(item: FactureItem, homeViewModel: HomeViewModel) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val file by homeViewModel.downloadState.collectAsState()
    var enabled by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(file) {}
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.number, style = TextStyle(
                fontSize = 16.sp, fontWeight = FontWeight.Bold
            )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatusIndicator(item)

            IconButton(onClick = {
                homeViewModel.downloadInvoice(item.number, context)
                enabled = true

            }) {
                Icon(
                    imageVector = DownloadIcon, contentDescription = "Descargar", tint = Color.Blue
                )
            }

            IconButton(onClick = {

                if (item.fileUrl != null) {
                    homeViewModel.sharePdf(context, item.fileUrl!!)
                } else {
                    Toast.makeText(context, "No hay PDF para compartir", Toast.LENGTH_SHORT).show()
                }
            }, enabled = enabled) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Compartir",
                    tint = if (enabled) Color.Green else Color.Gray
                )
            }
        }
    }

}

@Composable
fun StatusIndicator(item: FactureItem) {
    if (item.status == 1) {
        Icon(
            imageVector = Icons.Filled.Check, contentDescription = "Válido", tint = Color.Green
        )
    } else {
        Icon(
            imageVector = Icons.Filled.Close, contentDescription = "Inválido", tint = Color.Red
        )
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
