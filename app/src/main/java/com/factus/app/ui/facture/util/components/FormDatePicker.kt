package com.factus.app.ui.facture.util.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.factus.app.ui.facture.DatePickerField

@Composable
fun FormDatePicker(
    label: String, value: String?, modifier: Modifier = Modifier, onValueChange: (String) -> Unit
) {
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DatePickerField(
            label = label, selectedDate = value, onDateSelected = onValueChange
        )
    }
}