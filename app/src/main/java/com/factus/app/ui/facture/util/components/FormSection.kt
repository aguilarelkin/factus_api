package com.factus.app.ui.facture.util.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FormSection(
    label1: String,
    value1: String?,
    onValueChange1: (String) -> Unit,
    label2: String,
    value2: String?,
    onValueChange2: (String) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        FormColumn(
            label = label1,
            value = value1,
            modifier = Modifier.weight(1f),
            onValueChange = onValueChange1
        )
        Spacer(modifier = Modifier.width(8.dp))
        FormColumn(
            label = label2,
            value = value2,
            modifier = Modifier.weight(1f),
            onValueChange = onValueChange2
        )
    }
}