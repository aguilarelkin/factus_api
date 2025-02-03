package com.factus.app.ui.facture

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.collectAsState
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
import com.factus.app.domain.models.IdentificationDocument
import com.factus.app.domain.models.LegalOrganization
import com.factus.app.domain.models.Location
import com.factus.app.domain.models.Numbering
import com.factus.app.domain.models.Payment
import com.factus.app.domain.models.PaymentMethodCode
import com.factus.app.domain.models.Product
import com.factus.app.domain.models.TributeClient
import com.factus.app.domain.models.WithholdingTax
import com.factus.app.domain.state.LoginResult
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FactureScreen(
    factureViewModel: FactureViewModel, navController: NavHostController, modifier: Modifier
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                "Factus", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
        }, navigationIcon = {
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
        val state by factureViewModel.numberingRangesState.collectAsState()

        when (state) {
            is LoginResult.Error -> {
                val errorMessage = (state as LoginResult.Error<*>).message
                Text(
                    text = "Error: $errorMessage", color = Color.Red
                )
            }

            is LoginResult.Loading -> {
                val data = (state as LoginResult.Loading<*>).isLoading
                if (data) {
                    CircularProgressIndicator()
                }
            }

            is LoginResult.Success -> {
                DataFacture(innerPadding, factureViewModel, navController)
            }
        }
    })
}

private fun getProductList(): List<Product> {
    return listOf(
        Product(
            codeReference = "12345",
            name = "Laptop Dell XPS 13",
            quantity = 1,
            discountRate = 20,
            price = 1500000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "06", withholdingTaxRate = "7.00"), // ReteRenta
                WithholdingTax(code = "05", withholdingTaxRate = "15.00") // ReteIVA
            )
        ), Product(
            codeReference = "67890",
            name = "Smartphone Samsung Galaxy S21",
            quantity = 2,
            discountRate = 10,
            price = 900000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = emptyList()
        ), Product(
            codeReference = "11223",
            name = "Tablet Apple iPad Pro",
            quantity = 3,
            discountRate = 15,
            price = 1200000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "06", withholdingTaxRate = "7.00") // ReteRenta
            )
        ), Product(
            codeReference = "44556",
            name = "Auriculares Bose QuietComfort",
            quantity = 4,
            discountRate = 25,
            price = 350000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "06", withholdingTaxRate = "10.00") // ReteICA
            )
        ), Product(
            codeReference = "78901",
            name = "Reloj Casio G-Shock",
            quantity = 5,
            discountRate = 30,
            price = 500000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = emptyList()
        ), Product(
            codeReference = "23456",
            name = "Impresora HP LaserJet",
            quantity = 6,
            discountRate = 5,
            price = 800000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "05", withholdingTaxRate = "15.00")
            )
        ), Product(
            codeReference = "34567",
            name = "Monitor LG UltraWide",
            quantity = 7,
            discountRate = 20,
            price = 600000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "06", withholdingTaxRate = "8.00") // ReteRenta
            )
        ), Product(
            codeReference = "45678",
            name = "Teclado Logitech MX Keys",
            quantity = 8,
            discountRate = 10,
            price = 250000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = emptyList()
        ), Product(
            codeReference = "56789",
            name = "Mouse Microsoft Arc",
            quantity = 9,
            discountRate = 15,
            price = 150000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "05", withholdingTaxRate = "14.00") // ReteIVA
            )
        ), Product(
            codeReference = "67801",
            name = "Cámara Canon EOS Rebel",
            quantity = 10,
            discountRate = 0,
            price = 2000000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "06", withholdingTaxRate = "9.00") // ReteICA
            )
        )
    )

}

@Composable
fun DataFacture(
    innerPadding: PaddingValues,
    factureViewModel: FactureViewModel,
    navController: NavHostController
) {
    val customerData = rememberSaveable(stateSaver = CustomerSaver) { mutableStateOf(Customer()) }
    val billingData = remember { mutableStateOf(BillingPeriod()) }
    val factureData = remember {
        mutableStateOf(Facture())

    }
    val factureState by factureViewModel.factureState.collectAsState()
    var enabled by remember { mutableStateOf(true) }

    val stateNumbering by factureViewModel.numberingRangesState.collectAsState()
    val stateLocations by factureViewModel.locationsState.collectAsState()
    val paymentMethods = listOf(
        PaymentMethodCode(10, "Efectivo"),
        PaymentMethodCode(42, "Consignación"),
        PaymentMethodCode(20, "Cheque"),
        PaymentMethodCode(47, "Transferencia"),
        PaymentMethodCode(71, "Bonos"),
        PaymentMethodCode(72, "Vales"),
        PaymentMethodCode(1, "Medio de pago no definido"),
        PaymentMethodCode(49, "Tarjeta Débito"),
        PaymentMethodCode(48, "Tarjeta Crédito")
    )
    val identificationDocuments = listOf(
        IdentificationDocument(1, "Registro civil"),
        IdentificationDocument(2, "Tarjeta de identidad"),
        IdentificationDocument(3, "Cédula de ciudadanía"),
        IdentificationDocument(4, "Tarjeta de extranjería"),
        IdentificationDocument(5, "Cédula de extranjería"),
        IdentificationDocument(6, "NIT"),
        IdentificationDocument(7, "Pasaporte"),
        IdentificationDocument(8, "Documento de identificación extranjero"),
        IdentificationDocument(9, "PEP"),
        IdentificationDocument(10, "NIT otro país"),
        IdentificationDocument(11, "NUIP*")
    )

    val organizations = listOf(
        LegalOrganization(1, "Persona Jurídica"), LegalOrganization(2, "Persona Natural")
    )
    val tributes = listOf(
        TributeClient(18, "IVA"), TributeClient(21, "No aplica *")
    )

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

            Row(modifier = Modifier.fillMaxWidth()) {
                if (factureData.value.payment_form == "2") {
                    FormDatePiker(label = "FECHA DE VENCIMIENTO",
                        value = if (factureData.value.payment_due_date == null) "" else factureData.value.payment_due_date.toString(),
                        modifier = Modifier.weight(1f),
                        onValueChange = {
                            updateFactureData(
                                factureData, paymentDueDate = LocalDate.parse(it, formatter)
                            )
                        })
                    Spacer(modifier = Modifier.width(8.dp))
                }
                ListDropdown(
                    info = "RANGO DE NUMERACIÓN",
                    categories = stateNumbering.data ?: emptyList(),
                    selectedCategory = Numbering(id = 0, document = ""),
                    onCategorySelected = {
                        updateFactureData(
                            factureData, numberingRangeId = it.id
                        )
                    },
                    itemToString = { it.document },
                    modifier = Modifier.weight(1f),

                    )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                ListDropdown(
                    info = "FORMA DE PAGO",
                    categories = listOf(
                        Payment(id = 1, name = "Contado"), Payment(id = 2, name = "Crédito")
                    ),
                    selectedCategory = Payment(id = 0, name = ""),
                    onCategorySelected = {
                        updateFactureData(
                            factureData, paymentForm = it.id.toString()
                        )
                    },
                    itemToString = { it.name },
                    modifier = Modifier.weight(1f),
                )
                Spacer(modifier = Modifier.width(8.dp))
                ListDropdown(
                    info = "CÓDIGO DE PAGO",
                    categories = paymentMethods,
                    selectedCategory = PaymentMethodCode(code = 0, description = ""),
                    onCategorySelected = {
                        updateFactureData(
                            factureData, paymentMethodCode = it.code.toString()
                        )
                    },
                    itemToString = { it.description },
                    modifier = Modifier.weight(1f),
                )
            }

            FormTime(label1 = "FECHA INICIO",
                value1 = if (billingData.value.start_date == null) "" else billingData.value.start_date.toString(),
                onValueChange1 = {
                    updateBillingData(
                        billingData, startDate = LocalDate.parse(it, formatter)
                    )
                },
                label2 = "HORA INICIO",
                value2 = billingData.value.start_time,
                onValueChange2 = { updateBillingData(billingData, startTime = it) })

            FormTime(label1 = "FECHA FINAL",
                value1 = if (billingData.value.end_date == null) "" else billingData.value.end_date.toString(),
                onValueChange1 = {
                    updateBillingData(
                        billingData, endDate = LocalDate.parse(it, formatter)

                    )
                },
                label2 = "HORA FINAL",
                value2 = billingData.value.end_time,
                onValueChange2 = { updateBillingData(billingData, endTime = it) })
            FormColumn(label = "OBSERVACIÓN",
                value = factureData.value.observation,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    updateFactureData(
                        factureData, observation = it
                    )
                })
            // Cliente Section
            ClientSection(customerData)
            Row(modifier = Modifier.fillMaxWidth()) {
                FormColumn(label = "IDENTIFICACIÓN",
                    value = customerData.value.identification,
                    modifier = Modifier.weight(1f),
                    onValueChange = {
                        updateCustomerData(
                            customerData, identification = it
                        )
                    })
                Spacer(modifier = Modifier.width(8.dp))
                Text(customerData.value.identificationDocumentId.toString())

                ListDropdown(
                    info = "ID DOCUMENTO",
                    categories = identificationDocuments,
                    selectedCategory = IdentificationDocument(id = 0, name = ""),
                    onCategorySelected = {
                        updateCustomerData(
                            customerData, identificationDocumentId = it.id
                        )
                        Log.e("error", customerData.value.identificationDocumentId.toString())
                    },
                    itemToString = { it.name },
                    modifier = Modifier.weight(1f),
                )
            }
            FormSection(label1 = "NOMBRE",
                value1 = customerData.value.names,
                onValueChange1 = { updateCustomerData(customerData, names = it) },
                label2 = "DIRECCIÓN",
                value2 = customerData.value.address,
                onValueChange2 = { updateCustomerData(customerData, address = it) })

            Row(modifier = Modifier.fillMaxWidth()) {
                if (customerData.value.legalOrganizationId.equals("1")) {
                    FormColumn(label = "EMPRESA",
                        value = customerData.value.company,
                        modifier = Modifier.weight(1f),
                        onValueChange = {
                            updateCustomerData(
                                customerData, company = it
                            )
                        })
                    Spacer(modifier = Modifier.width(8.dp))
                }
                FormColumn(label = "NOMBRE COMERCIAL",
                    value = customerData.value.tradeName,
                    modifier = Modifier.weight(1f),
                    onValueChange = {
                        updateCustomerData(
                            customerData, tradeName = it
                        )
                    })
            }

            FormSection(label1 = "EMAIL",
                value1 = customerData.value.email,
                onValueChange1 = { updateCustomerData(customerData, email = it) },
                label2 = "TELÉFONO",
                value2 = customerData.value.phone,
                onValueChange2 = { updateCustomerData(customerData, phone = it) })
            Row(modifier = Modifier.fillMaxWidth()) {
                ListDropdown(
                    info = "ORGANIZACIÓN LEGAL",
                    categories = organizations,
                    selectedCategory = LegalOrganization(legal_organization_id = 0, nombre = ""),
                    onCategorySelected = {
                        updateCustomerData(
                            customerData, legalOrganizationId = it.legal_organization_id.toString()
                        )
                    },
                    itemToString = { it.nombre },
                    modifier = Modifier.weight(1f),
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (customerData.value.identificationDocumentId == 10 || customerData.value.identificationDocumentId == 6) {
                    FormColumn(
                        label = "DV",
                        value = customerData.value.dv,
                        modifier = Modifier.weight(1f),
                        onValueChange = {
                            updateCustomerData(
                                customerData, dv = it
                            )
                        },
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                ListDropdown(
                    info = "TRIBUTO",
                    categories = tributes,
                    selectedCategory = TributeClient(tribute_id = 0, nombre = ""),
                    onCategorySelected = {
                        updateCustomerData(
                            customerData, tributeId = it.tribute_id.toString()
                        )
                    },
                    itemToString = { it.nombre },
                    modifier = Modifier.weight(1f),
                )
                Spacer(modifier = Modifier.width(8.dp))
                ListDropdown(
                    info = "MUNICIPALIDAD",
                    categories = stateLocations.data ?: emptyList(),
                    selectedCategory = Location(
                        id = 0, name = "", code = "", department = ""
                    ),
                    onCategorySelected = {
                        updateCustomerData(
                            customerData, municipalityId = it.id.toString()
                        )
                    },
                    itemToString = { it.name },
                    modifier = Modifier.weight(1f),
                )
            }

            // Producto List
            LevelText("PRODUCTOS")
        }

        items(productList) { ProductItem(it) }

        when (factureState) {
            is LoginResult.Error -> {
                val errorMessage = (factureState as LoginResult.Error<Facture>).message
                item {
                    enabled = true
                    Text(
                        text = "Error: $errorMessage", color = Color.Red
                    )
                }
            }

            is LoginResult.Loading -> {
                val data = (factureState as LoginResult.Loading<*>).isLoading
                enabled = !data
                if (data) {
                    item {
                        CircularProgressIndicator()
                    }
                }
            }

            is LoginResult.Success -> {
                enabled = false
                Toast.makeText(context, "Factura creada exitosamente", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            ActionButton(enabled = enabled, onClick = {
                factureData.value = factureData.value.copy(
                    customer = customerData.value,
                    billing_period = billingData.value,
                    items = productList
                )
                val isValid =
                    validateCustomerData(factureData.value) && isValidFacture(factureData.value)
                if (isValid) {
                    factureViewModel.createdFacture(factureData.value)
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
    label: String, selectedDate: String?, onDateSelected: (String) -> Unit
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
        FieldName(dataValue = selectedDate, enabled = false) { }
    }
}

@Composable
fun TimePickerField(
    label: String, selectedTime: String?, onTimeSelected: (String) -> Unit
) {
    var openTimePicker by remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    if (openTimePicker) {
        TimePickerDialog(
            LocalContext.current, { _, selectedHour, selectedMinute ->
                val currentSecond = Calendar.getInstance().get(Calendar.SECOND)
                val formattedTime = String.format(
                    Locale.ROOT, "%02d:%02d:%02d", selectedHour, selectedMinute, currentSecond
                )
                onTimeSelected(formattedTime)
            }, hour, minute, true
        ).show()
        openTimePicker = false
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { openTimePicker = true },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label)
        Text(
            text = selectedTime ?: "", style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Composable
fun FormSection(
    label1: String,
    value1: String?,
    onValueChange1: (String) -> Unit,
    label2: String,
    value2: String?,
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
fun FormTime(
    label1: String,
    value1: String?,
    onValueChange1: (String) -> Unit,
    label2: String,
    value2: String?,
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
fun FormColumn(label: String, value: String?, modifier: Modifier, onValueChange: (String) -> Unit) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        LevelText(label)
        FieldName(dataValue = value) { onValueChange(it) }
    }
}

@Composable
fun FormDatePiker(
    label: String, value: String?, modifier: Modifier, onValueChange: (String) -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        DatePickerField(label, value) { onValueChange(it) }
    }
}

@Composable
fun FormTime(
    label: String, value: String?, modifier: Modifier, onValueChange: (String) -> Unit
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
fun ActionButton(enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
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

fun validateCustomerData(facture: Facture): Boolean {
    if (facture.payment_form == "2" && facture.payment_due_date == null) {
        return false
    }
    if ((facture.customer.identificationDocumentId == 6 || facture.customer.identificationDocumentId == 10) && facture.customer.dv.isNullOrBlank()) {
        return false
    }
    if (facture.customer.legalOrganizationId == "1" && facture.customer.company.isNullOrBlank()) {
        return false
    }
    return true
}

fun isValidFacture(facture: Facture): Boolean {

    if (facture.numbering_range_id <= 0) return false
    if (facture.reference_code.isBlank()) return false
    //if (facture.observation.isBlank()) return false
    if (facture.payment_form.isBlank()) return false
    //if (facture.payment_due_date == null) return false
    if (facture.payment_method_code.isBlank()) return false

    val bp = facture.billing_period ?: return false
    if (bp.start_date == null) return false
    if (bp.start_time.isNullOrBlank()) return false
    if (bp.end_date == null) return false
    if (bp.end_time.isNullOrBlank()) return false


    val customer = facture.customer
    if (customer.identification.isBlank()) return false
    //if (customer.dv.isNullOrBlank()) return false
    //if (customer.company.isNullOrBlank()) return false
    //if (customer.tradeName.isNullOrBlank()) return false
    if (customer.names.isNullOrBlank()) return false
    if (customer.address.isNullOrBlank()) return false
    if (customer.email.isNullOrBlank()) return false
    if (customer.phone.isNullOrBlank()) return false
    if (customer.legalOrganizationId.isNullOrBlank()) return false
    if (customer.tributeId.isBlank()) return false
    if (customer.identificationDocumentId == 0) return false
    if (customer.municipalityId.isBlank()) return false

    if (facture.items.isEmpty()) return false
    facture.items.forEach { product ->
        if (product.name.isBlank()) return false
    }
    return true
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
    identificationDocumentId: Int? = null,
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
        payment_due_date = (paymentDueDate ?: customerData.value.payment_due_date).toString(),
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
        start_date = (startDate ?: customerData.value.start_date).toString(),
        start_time = startTime ?: customerData.value.start_time,
        end_date = (endDate ?: customerData.value.end_date).toString(),
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
private fun FieldName(dataValue: String?, enabled: Boolean = true, dataOnChange: (String) -> Unit) {
    TextField(
        value = if (dataValue.isNullOrEmpty()) "" else dataValue,
        enabled = enabled,
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        onValueChange = { dataOnChange(it) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ListDropdown(
    info: String,
    categories: List<T>,
    selectedCategory: T,
    onCategorySelected: (T) -> Unit,
    itemToString: (T) -> String = { it.toString() },
    modifier: Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(itemToString(selectedCategory)) }

    LaunchedEffect(selectedCategory) {
        text = itemToString(selectedCategory)
    }
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        LevelText(info)
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = text,
                onValueChange = {},
                readOnly = true,
                //label = { Text(info) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable)

            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(text = { Text(itemToString(category)) }, onClick = {
                        text = itemToString(category)
                        onCategorySelected(category)
                        expanded = false
                    })
                }
            }
        }
    }
}