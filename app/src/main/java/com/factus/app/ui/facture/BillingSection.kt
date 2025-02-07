package com.factus.app.ui.facture

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.factus.app.domain.models.BillingPeriod
import com.factus.app.ui.facture.util.components.FormTimeSection
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun BillingSection(billingData: MutableState<BillingPeriod>, formatter: DateTimeFormatter) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .animateContentSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FormTimeSection(labelDate = "FECHA INICIO",
                valueDate = billingData.value.start_date ?: "",
                onDateChange = { dateStr ->
                    try {
                        val parsedDate = LocalDate.parse(dateStr, formatter)
                        billingData.value =
                            billingData.value.copy(start_date = parsedDate.toString())
                    } catch (e: Exception) {
                        // Manejo de error: Se podría mostrar un mensaje o registrar el error
                    }
                },
                labelTime = "HORA INICIO",
                valueTime = billingData.value.start_time,
                onTimeChange = { newTime ->
                    billingData.value = billingData.value.copy(start_time = newTime)
                })
            FormTimeSection(labelDate = "FECHA FINAL",
                valueDate = billingData.value.end_date ?: "",
                onDateChange = { dateStr ->
                    try {
                        val parsedDate = LocalDate.parse(dateStr, formatter)
                        billingData.value = billingData.value.copy(end_date = parsedDate.toString())
                    } catch (e: Exception) {
                        // Manejo de error
                    }
                },
                labelTime = "HORA FINAL",
                valueTime = billingData.value.end_time,
                onTimeChange = { newTime ->
                    billingData.value = billingData.value.copy(end_time = newTime)
                })
        }
    }
}
