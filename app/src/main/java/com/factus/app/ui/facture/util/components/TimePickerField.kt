package com.factus.app.ui.facture.util.components

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import java.util.Locale

@Composable
fun TimePickerField(
    label: String,
    selectedTime: String?,
    modifier: Modifier = Modifier,
    onTimeSelected: (String) -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }
    if (openDialog) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        TimePickerDialog(
            LocalContext.current, { _, selectedHour, selectedMinute ->
                val currentSecond = Calendar.getInstance().get(Calendar.SECOND)
                val formattedTime = String.format(
                    Locale.getDefault(),
                    "%02d:%02d:%02d",
                    selectedHour,
                    selectedMinute,
                    currentSecond
                )
                onTimeSelected(formattedTime)
            }, hour, minute, true
        ).show()
        openDialog = false
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { openDialog = true },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label)
        Text(text = selectedTime ?: "", style = MaterialTheme.typography.bodyLarge)
    }
}