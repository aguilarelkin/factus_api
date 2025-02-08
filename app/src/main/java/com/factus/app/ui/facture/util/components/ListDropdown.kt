package com.factus.app.ui.facture.util.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.factus.app.ui.theme.CustomFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ListDropdown(
    label: String,
    items: List<T>,
    selectedItem: T,
    modifier: Modifier = Modifier,
    itemToString: (T) -> String,
    onItemSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var currentText by remember { mutableStateOf(itemToString(selectedItem)) }

    LaunchedEffect(selectedItem) {
        currentText = itemToString(selectedItem)
    }

    Column(
        modifier = modifier.padding(vertical = 1.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = currentText,
                onValueChange = { /* Campo de solo lectura */ },
                readOnly = false,
                label = { Text(label, color = Color.Black) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Black, unfocusedIndicatorColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryEditable)
                    .heightIn(min = 56.dp),
                singleLine = true
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                items.forEach { item ->
                    DropdownMenuItem(text = { Text(itemToString(item), fontFamily = CustomFont) },
                        onClick = {
                            currentText = itemToString(item)
                            onItemSelected(item)
                            expanded = false
                        })
                }
            }
        }
    }
}
