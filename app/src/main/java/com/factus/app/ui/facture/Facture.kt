package com.factus.app.ui.facture

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/*
{
    "numbering_range_id": 4,
    "reference_code": "I3",
    "observation": "",
    "payment_form": "1",
	"payment_due_date": "2024-12-30",
    "payment_method_code": "10",
	"billing_period": {
        "start_date": "2024-01-10",
        "start_time": "00:00:00",
        "end_date": "2024-02-09",
        "end_time": "23:59:59"
    },
    "customer": {
        "identification": "123456789",
        "dv": "3",
        "company": "",
        "trade_name": "",
        "names": "Alan Turing",
        "address": "calle 1 # 2-68",
        "email": "alanturing@enigmasas.com",
        "phone": "1234567890",
        "legal_organization_id": "2",
        "tribute_id": "21",
        "identification_document_id": "3",
        "municipality_id": "980"
    },
    "items": [
        {
            "code_reference": "12345",
            "name": "producto de prueba",
            "quantity": 1,
            "discount_rate": 20,
            "price": 50000,
            "tax_rate": "19.00",
            "unit_measure_id": 70,
            "standard_code_id": 1,
            "is_excluded": 0,
            "tribute_id": 1,
            "withholding_taxes": [
                {
                    "code": "06",
                    "withholding_tax_rate": "7.00"
                },
                {
                    "code": "05",
                    "withholding_tax_rate": "15.00"
                }
            ]
        },
        {
            "code_reference": "54321",
            "name": "producto de prueba 2",
            "quantity": 1,
            "discount_rate": 0,
            "price": 50000,
            "tax_rate": "5.00",
            "unit_measure_id": 70,
            "standard_code_id": 1,
            "is_excluded": 0,
            "tribute_id": 1,
            "withholding_taxes": []
        }
    ]
}
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FactureScreen() {
    Scaffold(topBar = {
        TopAppBar(title = { Text("") }, navigationIcon = {
            IconButton(onClick = {
                // navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        })
    }, content = { innerPadding ->
        DataFacture(innerPadding)
    })
}

@Composable
fun DataFacture(innerPadding: PaddingValues) {
    LazyColumn(
        contentPadding = innerPadding,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LevelText("Factura")
                Spacer(modifier = Modifier.height(8.dp))
                LevelText("000000100")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
/*                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("Nombre")
                    FieldName(dataValue = productData.value.title) {
                        productData.value = productData.value.copy(title = it)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("Cedula")
                    FieldName(dataValue = productData.value.price.toString()) {
                        productData.value = productData.value.copy(
                            price = validDouble(it)
                        )
                    }
                }*/
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun LevelText(text: String) {
    Text(
        text = text, modifier = Modifier.wrapContentSize()
    )
}

@Composable
private fun FieldName(dataValue: String, dataOnChange: (String) -> Unit) {
    TextField(
        value = dataValue,
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        onValueChange = { dataOnChange(it) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDropdown(
    info: String,
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(selectedCategory) }
    LaunchedEffect(key1 = selectedCategory) {
        text = selectedCategory
    }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = text,
            onValueChange = {},
            readOnly = true,
            label = { Text(info) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.menuAnchor(type = MenuAnchorType.PrimaryEditable)
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            categories.forEach { category ->
                DropdownMenuItem(text = { Text(text = category) }, onClick = {
                    text = category
                    onCategorySelected(category)
                    expanded = false
                })
            }
        }
    }
}