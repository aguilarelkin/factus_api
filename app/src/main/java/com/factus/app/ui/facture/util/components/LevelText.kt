package com.factus.app.ui.facture.util.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun LevelText(text: String) {
    Text(
        text = text, modifier = Modifier.wrapContentSize(), textAlign = TextAlign.Center
    )
}