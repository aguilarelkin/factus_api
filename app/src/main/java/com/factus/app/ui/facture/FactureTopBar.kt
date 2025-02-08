package com.factus.app.ui.facture

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.factus.app.ui.theme.CustomFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FactureTopBar(onBackClick: () -> Unit) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = "Factus",
            fontFamily = CustomFont,
            color = Color(0xFF424242)
        )
    }, navigationIcon = {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFF424242) // Gris oscuro para íconos
            )
        }
    }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = Color(0xFFF5F5F5) // Gris claro para un diseño limpio
    )
    )
}

