package com.factus.app.ui.facture.util.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FormTimeSection(
    labelDate: String,
    valueDate: String,
    onDateChange: (String) -> Unit,
    labelTime: String,
    valueTime: String?,
    onTimeChange: (String) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        FormDatePicker(
            label = labelDate,
            value = valueDate,
            modifier = Modifier.weight(1f),
            onValueChange = onDateChange
        )
        Spacer(modifier = Modifier.width(8.dp))
        FormTimePicker(
            label = labelTime,
            value = valueTime ?: "",
            modifier = Modifier.weight(1f),
            onValueChange = onTimeChange
        )
    }
}