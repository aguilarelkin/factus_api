package com.factus.app.ui.facture

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.factus.app.domain.models.Customer
import com.factus.app.domain.models.CustomerSaver
import com.factus.app.domain.models.Product
import com.factus.app.domain.models.WithholdingTax
import java.util.Calendar

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
fun FactureScreen(
    factureViewModel: FactureViewModel, navController: NavHostController, modifier: Modifier
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("Factus") }, navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        })
    }, content = { innerPadding ->
        DataFacture(innerPadding)
    })
}

private fun getProductList(): List<Product> {
    return listOf(
        Product(
            codeReference = "12345",
            name = "Producto de prueba 1",
            quantity = 1,
            discountRate = 20,
            price = 50000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "06", withholdingTaxRate = "7.00"),
                WithholdingTax(code = "05", withholdingTaxRate = "15.00")
            )
        ), Product(
            codeReference = "67890",
            name = "Producto de prueba 2",
            quantity = 2,
            discountRate = 10,
            price = 30000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = emptyList() // Lista vacía de impuestos de retención
        ), Product(
            codeReference = "11223",
            name = "Producto de prueba 3",
            quantity = 3,
            discountRate = 15,
            price = 20000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "06", withholdingTaxRate = "7.00")
            )
        ), Product(
            codeReference = "44556",
            name = "Producto de prueba 4",
            quantity = 4,
            discountRate = 25,
            price = 15000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "07", withholdingTaxRate = "10.00")
            )
        ), Product(
            codeReference = "78901",
            name = "Producto de prueba 5",
            quantity = 5,
            discountRate = 30,
            price = 40000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = emptyList()
        ), Product(
            codeReference = "23456",
            name = "Producto de prueba 6",
            quantity = 6,
            discountRate = 5,
            price = 70000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "08", withholdingTaxRate = "12.00")
            )
        ), Product(
            codeReference = "34567",
            name = "Producto de prueba 7",
            quantity = 7,
            discountRate = 20,
            price = 55000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "06", withholdingTaxRate = "8.00")
            )
        ), Product(
            codeReference = "45678",
            name = "Producto de prueba 8",
            quantity = 8,
            discountRate = 10,
            price = 25000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = emptyList()
        ), Product(
            codeReference = "56789",
            name = "Producto de prueba 9",
            quantity = 9,
            discountRate = 15,
            price = 60000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "05", withholdingTaxRate = "14.00")
            )
        ), Product(
            codeReference = "67801",
            name = "Producto de prueba 10",
            quantity = 10,
            discountRate = 0,
            price = 120000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "07", withholdingTaxRate = "9.00")
            )
        )
    )
}


/*@Composable
fun DataFacture(innerPadding: PaddingValues) {
    val customerData = rememberSaveable(stateSaver = CustomerSaver) { mutableStateOf(Customer()) }
    val context = LocalContext.current
    val product = getProductList()
    LazyColumn(
        contentPadding = innerPadding,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            //Caber
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LevelText("Factura")
                Spacer(modifier = Modifier.height(8.dp))
                LevelText("reference_code")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("FECHA DE VENCIMIENTO")
                    FieldName(dataValue = customerData.value.dv) {
                        customerData.value = customerData.value.copy(dv = it)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("RANGO DE NUMERACIÓN")
                    FieldName(dataValue = customerData.value.identification) {
                        customerData.value = customerData.value.copy(identification = it)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("FORMA DE PAGO")
                    FieldName(dataValue = customerData.value.identification) {
                        customerData.value = customerData.value.copy(identification = it)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("CÓDIGO DE PAGO")
                    FieldName(dataValue = customerData.value.identification) {
                        customerData.value = customerData.value.copy(identification = it)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LevelText("OBSERVACÍON")
                FieldName(dataValue = customerData.value.dv) {
                    customerData.value = customerData.value.copy(dv = it)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("CÓDIGO DE PAGO")
                    FieldName(dataValue = customerData.value.identification) {
                        customerData.value = customerData.value.copy(identification = it)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            //Period of fractionation
            LevelText("PERIODO DE FACTURACIÓN")
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("FECHA INICIO")
                    FieldName(dataValue = customerData.value.identification) {
                        customerData.value = customerData.value.copy(identification = it)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))

                // DV
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("HORA INICIO")
                    FieldName(dataValue = customerData.value.dv) {
                        customerData.value = customerData.value.copy(dv = it)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("FECHA FINAL")
                    FieldName(dataValue = customerData.value.identification) {
                        customerData.value = customerData.value.copy(identification = it)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))

                // DV
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("HORA FINAL")
                    FieldName(dataValue = customerData.value.dv) {
                        customerData.value = customerData.value.copy(dv = it)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Cliente
            LevelText("Cliente")
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Identificación
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("IDENTIFICACIÓN")
                    FieldName(dataValue = customerData.value.identification) {
                        customerData.value = customerData.value.copy(identification = it)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))

                // DV
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("DV")
                    FieldName(dataValue = customerData.value.dv) {
                        customerData.value = customerData.value.copy(dv = it)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Empresa
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("EMPRESA")
                    FieldName(dataValue = customerData.value.company) {
                        customerData.value = customerData.value.copy(company = it)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))

                // Nombre comercial (Trade Name)
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("NOMBRE COMERCIAL")
                    FieldName(dataValue = customerData.value.tradeName) {
                        customerData.value = customerData.value.copy(tradeName = it)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Nombres
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("NOMBRES")
                    FieldName(dataValue = customerData.value.names) {
                        customerData.value = customerData.value.copy(names = it)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("DIRECCIÓN")
                    FieldName(dataValue = customerData.value.address) {
                        customerData.value = customerData.value.copy(address = it)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Email
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("EMAIL")
                    FieldName(dataValue = customerData.value.email) {
                        customerData.value = customerData.value.copy(email = it)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("TELÉFONO")
                    FieldName(dataValue = customerData.value.phone) {
                        customerData.value = customerData.value.copy(phone = it)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // ID Organización Legal
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("ID TRIBUTO")
                    FieldName(dataValue = customerData.value.tributeId) {
                        customerData.value = customerData.value.copy(tributeId = it)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("ID MUNICIPIO")
                    FieldName(dataValue = customerData.value.municipalityId) {
                        customerData.value = customerData.value.copy(municipalityId = it)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ID Documento de Identificación
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("ID DOCUMENTO DE IDENTIFICACIÓN")
                    FieldName(dataValue = customerData.value.identificationDocumentId) {
                        customerData.value = customerData.value.copy(identificationDocumentId = it)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelText("ID ORGANIZACIÓN LEGAL")
                    FieldName(dataValue = customerData.value.legalOrganizationId) {
                        customerData.value = customerData.value.copy(legalOrganizationId = it)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            LevelText("PRODUCTOS")
        }

        items(product) {
            ProductItem(it)
        }


        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val isValid = customerData.value.let { customer ->
                        customer.identification.isNotBlank() && customer.dv.isNotBlank() && customer.names.isNotBlank() && customer.address.isNotBlank() && customer.email.isNotBlank() && customer.phone.isNotBlank() && customer.legalOrganizationId.isNotBlank() && customer.tributeId.isNotBlank() && customer.identificationDocumentId.isNotBlank() && customer.municipalityId.isNotBlank()
                    }
                    if (isValid) {
                        //dataOperation(id, productData.value, operationViewModel)
                    } else {
                        Toast.makeText(
                            context,
                            "¡Por favor, completa todos los campos necesarios!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                    disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(8.dp),
            ) {
                Text(
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp, fontWeight = FontWeight.Bold
                    ), text = "Crear factura", modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}*/

@Composable
fun DataFacture(innerPadding: PaddingValues) {
    val customerData = rememberSaveable(stateSaver = CustomerSaver) { mutableStateOf(Customer()) }
    val context = LocalContext.current
    val productList = getProductList()

    LazyColumn(
        contentPadding = innerPadding,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item { HeaderSection() }

        // Sección de formularios: fecha de vencimiento, rango de numeración, etc.
        item {

            FormSection(label1 = "FECHA DE VENCIMIENTO",
                value1 = customerData.value.dv,
                onValueChange1 = { updateCustomerData(customerData, dv = it) },
                label2 = "RANGO DE NUMERACIÓN",
                value2 = customerData.value.identification,
                onValueChange2 = { updateCustomerData(customerData, identification = it) })

            FormSection(label1 = "FORMA DE PAGO",
                value1 = customerData.value.identification,
                onValueChange1 = { updateCustomerData(customerData, identification = it) },
                label2 = "CÓDIGO DE PAGO",
                value2 = customerData.value.identification,
                onValueChange2 = { updateCustomerData(customerData, identification = it) })

            FormSection(label1 = "OBSERVACIÓN",
                value1 = customerData.value.dv,
                onValueChange1 = { updateCustomerData(customerData, dv = it) },
                label2 = "CÓDIGO DE PAGO",
                value2 = customerData.value.identification,
                onValueChange2 = { updateCustomerData(customerData, identification = it) })

            FormSection(label1 = "FECHA INICIO",
                value1 = customerData.value.identification,
                onValueChange1 = { updateCustomerData(customerData, identification = it) },
                label2 = "HORA INICIO",
                value2 = customerData.value.dv,
                onValueChange2 = { updateCustomerData(customerData, dv = it) })

            FormSection(label1 = "FECHA FINAL",
                value1 = customerData.value.identification,
                onValueChange1 = { updateCustomerData(customerData, identification = it) },
                label2 = "HORA FINAL",
                value2 = customerData.value.dv,
                onValueChange2 = { updateCustomerData(customerData, dv = it) })

            // Cliente Section
            ClientSection(customerData)

            // Empresa Section
            CompanySection(customerData)

            // Producto List
            LevelText("PRODUCTOS")
        }

        items(productList) { ProductItem(it) }

        // Botón de acción
        item {
            Spacer(modifier = Modifier.height(16.dp))
            ActionButton(onClick = {
                val isValid = validateCustomerData(customerData.value)
                if (isValid) {
                    // Acción a realizar
                } else {
                    Toast.makeText(
                        context,
                        "¡Por favor, completa todos los campos necesarios!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun HeaderSection() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        LevelText("Factura")
        Spacer(modifier = Modifier.height(8.dp))
        LevelText("reference_code")
    }
}

@Composable
fun DatePickerField(
    label: String, selectedDate: String, onDateSelected: (String) -> Unit
) {
    var openDatePicker by remember { mutableStateOf(false) }

    // DatePicker Dialog state
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // Open date picker dialog
    if (openDatePicker) {
        DatePickerDialog(
            LocalContext.current, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate =
                    "$selectedDay/${selectedMonth + 1}/$selectedYear" // "dd/MM/yyyy"
                onDateSelected(formattedDate)
            }, year, month, day
        ).show()
        openDatePicker = false
    }

    // Render UI
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { openDatePicker = true }
        .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        LevelText(label)
        Spacer(modifier = Modifier.height(8.dp))
        FieldName(dataValue = selectedDate) { }
    }
}

@Composable
fun FormSection(
    label1: String,
    value1: String,
    onValueChange1: (String) -> Unit,
    label2: String,
    value2: String,
    onValueChange2: (String) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        FormColumn(
            label = label1,
            value = value1,
            modifier = Modifier.weight(1f),
            onValueChange = onValueChange1
        )
        Spacer(modifier = Modifier.width(8.dp))
        FormColumn(
            label = label2,
            value = value2,
            modifier = Modifier.weight(1f),
            onValueChange = onValueChange2
        )
    }
}

@Composable
fun FormColumn(label: String, value: String, modifier: Modifier, onValueChange: (String) -> Unit) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        LevelText(label)
        FieldName(dataValue = value) { onValueChange(it) }
    }
}

@Composable
fun ClientSection(customerData: MutableState<Customer>) {
    LevelText("Cliente")
    FormSection(label1 = "IDENTIFICACIÓN",
        value1 = customerData.value.identification,
        onValueChange1 = { updateCustomerData(customerData, identification = it) },
        label2 = "DV",
        value2 = customerData.value.dv,
        onValueChange2 = { updateCustomerData(customerData, dv = it) })
}

@Composable
fun CompanySection(customerData: MutableState<Customer>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        FormColumn(
            label = "EMPRESA", value = customerData.value.company, modifier = Modifier.weight(1f)
        ) {
            updateCustomerData(
                customerData, company = it
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        FormColumn(
            label = "NOMBRE COMERCIAL",
            value = customerData.value.tradeName,
            modifier = Modifier.weight(1f)
        ) { updateCustomerData(customerData, tradeName = it) }
    }
}

@Composable
fun ActionButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.buttonElevation(8.dp)
    ) {
        Text(
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 20.sp, fontWeight = FontWeight.Bold
            ), text = "Crear factura", modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

fun validateCustomerData(customer: Customer): Boolean {
    return customer.identification.isNotBlank() && customer.dv.isNotBlank() && customer.names.isNotBlank() && customer.address.isNotBlank() && customer.email.isNotBlank() && customer.phone.isNotBlank() && customer.legalOrganizationId.isNotBlank() && customer.tributeId.isNotBlank() && customer.identificationDocumentId.isNotBlank() && customer.municipalityId.isNotBlank()
}

fun updateCustomerData(
    customerData: MutableState<Customer>,
    dv: String? = null,
    identification: String? = null,
    company: String? = null,
    tradeName: String? = null
) {
    customerData.value = customerData.value.copy(
        dv = dv ?: customerData.value.dv,
        identification = identification ?: customerData.value.identification,
        company = company ?: customerData.value.company,
        tradeName = tradeName ?: customerData.value.tradeName
    )
}

@Composable
fun ProductItem(product: Product) {
    Card(modifier = Modifier.padding(16.dp)) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = product.name,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Precio:", modifier = Modifier.weight(1f) // Ocupa el espacio disponible
                )
                Text(text = "$${product.price}")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = "Descuento:", modifier = Modifier.weight(1f)
                )
                Text(text = "${product.discountRate}%")
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = "Impuesto:", modifier = Modifier.weight(1f)
                )
                Text(text = "${product.taxRate}%")
            }
            if (product.withholdingTaxes.isNotEmpty()) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Text(
                        text = "Retenciones:", fontWeight = FontWeight.Medium
                    )
                    product.withholdingTaxes.forEach { tax ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "- ${tax.code}")
                            Text(text = "${tax.withholdingTaxRate}%")
                        }
                    }
                }
            } else {
                Text(
                    text = "Sin retenciones", modifier = Modifier.padding(top = 12.dp)
                )
            }
        }
    }
}

@Composable
private fun LevelText(text: String) {
    Text(
        text = text, modifier = Modifier.wrapContentSize(), textAlign = TextAlign.Center
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
        OutlinedTextField(value = text,
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