package com.factus.app.ui.facture.util.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FormColumn(
    label: String, value: String?, modifier: Modifier = Modifier, onValueChange: (String) -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        LevelText(text = label)
        FieldName(dataValue = value ?: "", onValueChange = onValueChange)
    }
}
