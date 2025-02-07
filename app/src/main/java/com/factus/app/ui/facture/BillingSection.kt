package com.factus.app.ui.facture

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        FormTimeSection(labelDate = "FECHA INICIO",
            valueDate = billingData.value.start_date ?: "",
            onDateChange = { dateStr ->
                val parsedDate = LocalDate.parse(dateStr, formatter)
                billingData.value = billingData.value.copy(start_date = parsedDate.toString())
            },
            labelTime = "HORA INICIO",
            valueTime = billingData.value.start_time,
            onTimeChange = { billingData.value = billingData.value.copy(start_time = it) })
        FormTimeSection(labelDate = "FECHA FINAL",
            valueDate = billingData.value.end_date ?: "",
            onDateChange = { dateStr ->
                val parsedDate = LocalDate.parse(dateStr, formatter)
                billingData.value = billingData.value.copy(end_date = parsedDate.toString())
            },
            labelTime = "HORA FINAL",
            valueTime = billingData.value.end_time,
            onTimeChange = { billingData.value = billingData.value.copy(end_time = it) })
    }
}