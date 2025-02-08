package com.factus.app.ui.facture.util.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LevelText(
    text: String, modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val maxWidthDp = this.maxWidth

        Text(
            text = text,
            fontSize = if (maxWidthDp < 200.dp) 14.sp else 14.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}