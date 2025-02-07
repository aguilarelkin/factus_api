package com.factus.app.ui.facture.util.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ErrorText(message: String) {
    Text(
        text = message, color = Color.Red, modifier = Modifier.padding(16.dp)
    )
}