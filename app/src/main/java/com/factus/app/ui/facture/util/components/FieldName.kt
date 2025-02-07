package com.factus.app.ui.facture.util.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun FieldName(
    dataValue: String, enabled: Boolean = true, onValueChange: (String) -> Unit
) {
    TextField(
        value = dataValue,
        onValueChange = onValueChange,
        enabled = enabled,
        singleLine = true,
        shape = RoundedCornerShape(8.dp)
    )
}