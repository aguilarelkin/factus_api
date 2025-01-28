package com.factus.app.ui.facture

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import com.factus.app.domain.models.BillingPeriod
import com.factus.app.domain.models.Customer
import com.factus.app.domain.models.CustomerSaver
import com.factus.app.domain.models.Facture
import com.factus.app.domain.models.Product
import com.factus.app.domain.models.WithholdingTax
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.UUID

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

@Composable
fun DataFacture(innerPadding: PaddingValues) {
    val customerData = rememberSaveable(stateSaver = CustomerSaver) { mutableStateOf(Customer()) }
    val billingData = remember { mutableStateOf(BillingPeriod()) }
    val factureData = remember { mutableStateOf(Facture()) }

    val context = LocalContext.current
    val productList = getProductList()
    val formatter = DateTimeFormatter.ofPattern("d/M/yyyy")

    LazyColumn(
        contentPadding = innerPadding,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item { HeaderSection(factureData) }

        item {
            FormDatePiker(label1 = "FECHA DE VENCIMIENTO",
                value1 = factureData.value.payment_due_date.toString(),
                onValueChange1 = {
                    updateFactureData(factureData, paymentDueDate = LocalDate.parse(it, formatter))
                },
                label2 = "RANGO DE NUMERACIÓN",
                value2 = factureData.value.numbering_range_id.toString(),
                onValueChange2 = {
                    updateFactureData(
                        factureData, numberingRangeId = it.toIntOrNull() ?: 1
                    )
                })

            FormSection(label1 = "FORMA DE PAGO",
                value1 = factureData.value.payment_form,
                onValueChange1 = { updateFactureData(factureData, paymentForm = it) },
                label2 = "CÓDIGO DE PAGO",
                value2 = factureData.value.payment_method_code,
                onValueChange2 = { updateFactureData(factureData, paymentMethodCode = it) })

            FormSection(label1 = "OBSERVACIÓN",
                value1 = factureData.value.observation,
                onValueChange1 = { updateFactureData(factureData, observation = it) },
                label2 = "REFERENCIA",
                value2 = factureData.value.reference_code,
                onValueChange2 = { updateFactureData(factureData, referenceCode = it) })

            FormTime(label1 = "FECHA INICIO",
                value1 = billingData.value.start_date.toString(),
                onValueChange1 = {
                    updateBillingData(
                        billingData, startDate = LocalDate.parse(it, formatter)
                    )
                },
                label2 = "HORA INICIO",
                value2 = billingData.value.start_time,
                onValueChange2 = { updateBillingData(billingData, startTime = it) })

            FormTime(label1 = "FECHA FINAL",
                value1 = billingData.value.end_date.toString(),
                onValueChange1 = {
                    updateBillingData(
                        billingData, endDate = LocalDate.parse(it, formatter)
                    )
                },
                label2 = "HORA FINAL",
                value2 = billingData.value.end_time,
                onValueChange2 = { updateBillingData(billingData, endTime = it) })

            // Cliente Section
            ClientSection(customerData)

            FormSection(label1 = "IDENTIFICACIÓN",
                value1 = customerData.value.identification,
                onValueChange1 = { updateCustomerData(customerData, identification = it) },
                label2 = "DV",
                value2 = customerData.value.dv,
                onValueChange2 = { updateCustomerData(customerData, dv = it) })

            FormSection(label1 = "NOMBRE",
                value1 = customerData.value.names,
                onValueChange1 = { updateCustomerData(customerData, names = it) },
                label2 = "DIRECCIÓN",
                value2 = customerData.value.address,
                onValueChange2 = { updateCustomerData(customerData, address = it) })

            FormSection(label1 = "EMPRESA",
                value1 = customerData.value.company,
                onValueChange1 = { updateCustomerData(customerData, company = it) },
                label2 = "NOMBRE COMERCIAL",
                value2 = customerData.value.tradeName,
                onValueChange2 = { updateCustomerData(customerData, tradeName = it) })

            FormSection(label1 = "EMAIL",
                value1 = customerData.value.email,
                onValueChange1 = { updateCustomerData(customerData, email = it) },
                label2 = "TELÉFONO",
                value2 = customerData.value.phone,
                onValueChange2 = { updateCustomerData(customerData, phone = it) })

            FormSection(
                label1 = "ID ORGANIZACIÓN LEGAL",
                value1 = customerData.value.legalOrganizationId,
                onValueChange1 = { updateCustomerData(customerData, legalOrganizationId = it) },
                label2 = "ID DOCUMENTO IDENTIDAD",
                value2 = customerData.value.identificationDocumentId,
                onValueChange2 = {
                    updateCustomerData(
                        customerData, identificationDocumentId = it
                    )
                },
            )

            FormSection(label1 = "ID TRIBUTARIO",
                value1 = customerData.value.tributeId,
                onValueChange1 = { updateCustomerData(customerData, tributeId = it) },

                label2 = "ID MUNICIPALIDAD",
                value2 = customerData.value.municipalityId,
                onValueChange2 = { updateCustomerData(customerData, municipalityId = it) })

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
fun HeaderSection(factureData: MutableState<Facture>) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.Right) {
        val referenceCode = generateInvoiceReference()
        LaunchedEffect(referenceCode) {
            updateFactureData(factureData, referenceCode = referenceCode)
        }
        LevelText(referenceCode)
    }
}

private fun generateInvoiceReference(): String {
    val uniqueId = UUID.randomUUID().toString()
    return "FAC-${uniqueId.take(8).uppercase()}"
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { openDatePicker = true },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LevelText(label)
        FieldName(dataValue = selectedDate) { }
    }
}

@Composable
fun TimePickerField(
    label: String, selectedTime: String, onTimeSelected: (String) -> Unit
) {
    var openTimePicker by remember { mutableStateOf(false) }

    // Obtener hora y minuto actual
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    // Abrir el TimePickerDialog
    if (openTimePicker) {
        TimePickerDialog(
            LocalContext.current, { _, selectedHour, selectedMinute ->
                val formattedTime =
                    String.format(Locale.ROOT, "%02d:%02d", selectedHour, selectedMinute)
                onTimeSelected(formattedTime)
            }, hour, minute, true // true para formato de 24 horas, cambia a false para 12 horas
        ).show()
        openTimePicker = false
    }

    // Renderizar la UI
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { openTimePicker = true },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label)
        Text(
            text = selectedTime, style = MaterialTheme.typography.bodyLarge
        )
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
fun FormDatePiker(
    label1: String,
    value1: String,
    onValueChange1: (String) -> Unit,
    label2: String,
    value2: String,
    onValueChange2: (String) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        FormDatePiker(
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
fun FormTime(
    label1: String,
    value1: String,
    onValueChange1: (String) -> Unit,
    label2: String,
    value2: String,
    onValueChange2: (String) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        FormDatePiker(
            label = label1,
            value = value1,
            modifier = Modifier.weight(1f),
            onValueChange = onValueChange1
        )
        Spacer(modifier = Modifier.width(8.dp))
        FormTime(
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
fun FormDatePiker(
    label: String, value: String, modifier: Modifier, onValueChange: (String) -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        DatePickerField(label, value) { onValueChange(it) }
    }
}

@Composable
fun FormTime(
    label: String, value: String, modifier: Modifier, onValueChange: (String) -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        TimePickerField(label, value) { onValueChange(it) }
    }
}

@Composable
fun ClientSection(customerData: MutableState<Customer>) {
    LevelText("Cliente")
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
    tradeName: String? = null,
    names: String? = null,
    address: String? = null,
    email: String? = null,
    phone: String? = null,
    legalOrganizationId: String? = null,
    tributeId: String? = null,
    identificationDocumentId: String? = null,
    municipalityId: String? = null
) {
    customerData.value = customerData.value.copy(
        dv = dv ?: customerData.value.dv,
        identification = identification ?: customerData.value.identification,
        company = company ?: customerData.value.company,
        tradeName = tradeName ?: customerData.value.tradeName,
        names = names ?: customerData.value.names,
        address = address ?: customerData.value.address,
        email = email ?: customerData.value.email,
        phone = phone ?: customerData.value.phone,
        legalOrganizationId = legalOrganizationId ?: customerData.value.legalOrganizationId,
        tributeId = tributeId ?: customerData.value.tributeId,
        identificationDocumentId = identificationDocumentId
            ?: customerData.value.identificationDocumentId,
        municipalityId = municipalityId ?: customerData.value.municipalityId
    )
}

fun updateFactureData(
    customerData: MutableState<Facture>,
    numberingRangeId: Int? = null,
    referenceCode: String? = null,
    observation: String? = null,
    paymentForm: String? = null,
    paymentDueDate: LocalDate? = null,
    paymentMethodCode: String? = null,
    billingPeriod: BillingPeriod? = null,
    customer: Customer? = null,
    items: List<Product>? = null
) {
    customerData.value = customerData.value.copy(
        numbering_range_id = numberingRangeId ?: customerData.value.numbering_range_id,
        reference_code = referenceCode ?: customerData.value.reference_code,
        observation = observation ?: customerData.value.observation,
        payment_form = paymentForm ?: customerData.value.payment_form,
        payment_due_date = paymentDueDate ?: customerData.value.payment_due_date,
        payment_method_code = paymentMethodCode ?: customerData.value.payment_method_code,
        billing_period = billingPeriod ?: customerData.value.billing_period,
        customer = customer ?: customerData.value.customer,
        items = items ?: customerData.value.items
    )
}

fun updateBillingData(
    customerData: MutableState<BillingPeriod>,
    startDate: LocalDate? = null,
    startTime: String? = null,
    endDate: LocalDate? = null,
    endTime: String? = null
) {
    customerData.value = customerData.value.copy(
        start_date = startDate ?: customerData.value.start_date,
        start_time = startTime ?: customerData.value.start_time,
        end_date = endDate ?: customerData.value.end_date,
        end_time = endTime ?: customerData.value.end_time
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