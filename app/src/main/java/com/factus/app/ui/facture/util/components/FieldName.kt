package com.factus.app.ui.facture.util.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FieldName(
    dataValue: String,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit,
    label: String = "",
    placeholder: String = ""
) {
    OutlinedTextField(
        value = dataValue,
        onValueChange = onValueChange,
        enabled = enabled,
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        label = {
            if (label.isNotEmpty()) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        placeholder = {
            if (placeholder.isNotEmpty()) {
                Text(
                    text = placeholder, style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}