package com.factus.app.ui.facture

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.factus.app.domain.models.Facture
import java.util.UUID

@Composable
fun HeaderSection(factureData: MutableState<Facture>) {
    val referenceCode = remember { generateInvoiceReference() }

    LaunchedEffect(referenceCode) {
        factureData.value = factureData.value.copy(reference_code = referenceCode)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Referencia:", color = MaterialTheme.colorScheme.onSurface
            )

            AnimatedContent(targetState = referenceCode, label = "Reference Animation") { code ->
                Text(
                    text = code,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


private fun generateInvoiceReference(): String {
    val uniqueId = UUID.randomUUID().toString()
    return "FAC${uniqueId.take(8).uppercase()}"
}