package com.factus.app.ui.facture

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.factus.app.domain.models.Facture
import com.factus.app.ui.facture.util.components.FormColumn

@Composable
fun FactureObservationSection(factureData: MutableState<Facture>) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        FormColumn(
            label = "OBSERVACIÓN",
            value = factureData.value.observation,
            modifier = Modifier.fillMaxWidth()
        ) { newObservation ->
            factureData.value = factureData.value.copy(observation = newObservation)
        }
    }
}