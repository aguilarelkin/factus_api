package com.factus.app.ui.facture

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.factus.app.domain.models.BillingPeriod
import com.factus.app.ui.facture.util.components.TimePickerField
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun BillingSection(billingData: MutableState<BillingPeriod>, formatter: DateTimeFormatter) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .animateContentSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DatePickerField(label = "FECHA INICIO",
                        selectedDate = billingData.value.start_date ?: "",
                        onDateSelected = { dateStr ->
                            try {
                                val parsedDate = LocalDate.parse(dateStr, formatter)
                                billingData.value =
                                    billingData.value.copy(start_date = parsedDate.toString())
                            } catch (e: Exception) {
                                // Manejo de error: Se podría mostrar un mensaje o registrar el error
                            }
                        })
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TimePickerField(
                        label = "HORA INICIO", selectedTime = billingData.value.start_time,
                        onTimeSelected = { newTime ->
                            billingData.value = billingData.value.copy(start_time = newTime)
                        },
                    )
                }
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DatePickerField(label = "FECHA FINAL",
                        selectedDate = billingData.value.end_date ?: "",
                        onDateSelected = { dateStr ->
                            try {
                                val parsedDate = LocalDate.parse(dateStr, formatter)
                                billingData.value =
                                    billingData.value.copy(end_date = parsedDate.toString())
                            } catch (e: Exception) {
                                // Manejo de error
                            }
                        })
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TimePickerField(label = "HORA FINAL",
                        selectedTime = billingData.value.end_time,
                        onTimeSelected = { newTime ->
                            billingData.value = billingData.value.copy(end_time = newTime)
                        })
                }
            }
        }
    }
}
