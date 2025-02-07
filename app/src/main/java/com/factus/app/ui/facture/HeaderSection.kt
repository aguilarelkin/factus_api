package com.factus.app.ui.facture

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.factus.app.domain.models.Facture
import com.factus.app.ui.facture.util.components.LevelText
import java.util.UUID

@Composable
fun HeaderSection(factureData: MutableState<Facture>) {
    val referenceCode = remember { generateInvoiceReference() }
    LaunchedEffect(referenceCode) {
        factureData.value = factureData.value.copy(reference_code = referenceCode)
    }
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
    ) {
        LevelText(text = referenceCode)
    }
}

private fun generateInvoiceReference(): String {
    val uniqueId = UUID.randomUUID().toString()
    return "FAC${uniqueId.take(8).uppercase()}"
}