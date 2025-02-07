package com.factus.app.ui.facture

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.factus.app.ui.facture.util.components.FieldName
import com.factus.app.ui.facture.util.components.LevelText
import java.util.Calendar

@Composable
fun DatePickerField(
    label: String, selectedDate: String?, onDateSelected: (String) -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }
    if (openDialog) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(
            LocalContext.current, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                onDateSelected(formattedDate)
            }, year, month, day
        ).show()
        openDialog = false
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { openDialog = true },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LevelText(text = label)
        FieldName(dataValue = selectedDate ?: "", enabled = false, onValueChange = {})
    }
}