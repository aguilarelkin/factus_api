package com.factus.app.ui.facture

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.factus.app.domain.models.BillingPeriod
import com.factus.app.domain.models.Customer
import com.factus.app.domain.models.CustomerSaver
import com.factus.app.domain.models.Facture
import com.factus.app.domain.state.LoginResult
import com.factus.app.ui.facture.util.components.ActionButton
import com.factus.app.ui.facture.util.components.ErrorText
import com.factus.app.ui.facture.util.list.getIdentificationDocuments
import com.factus.app.ui.facture.util.list.getOrganizations
import com.factus.app.ui.facture.util.list.getPaymentMethods
import com.factus.app.ui.facture.util.list.getProductList
import com.factus.app.ui.facture.util.list.getTributes
import com.factus.app.ui.facture.util.validate.isValidFacture
import com.factus.app.ui.facture.util.validate.validateCustomerData
import java.time.format.DateTimeFormatter

@Composable
fun FactureScreen(
    factureViewModel: FactureViewModel, navController: NavController, modifier: Modifier
) {
    val numberingState by factureViewModel.numberingRangesState.collectAsState()
    val factureState by factureViewModel.factureState.collectAsState()
    val locationsState by factureViewModel.locationsState.collectAsState()

    val customerData = rememberSaveable(stateSaver = CustomerSaver) { mutableStateOf(Customer()) }
    val billingData = remember { mutableStateOf(BillingPeriod()) }
    val factureData = remember { mutableStateOf(Facture()) }

    val paymentMethods = getPaymentMethods()
    val identificationDocuments = getIdentificationDocuments()
    val organizations = getOrganizations()
    val tributes = getTributes()
    val productList = getProductList()

    val formatter = DateTimeFormatter.ofPattern("d/M/yyyy")
    val context = LocalContext.current

    Scaffold(topBar = { FactureTopBar(onBackClick = { navController.popBackStack() }) }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val isLoading = (factureState as? LoginResult.Loading<*>)?.isLoading ?: false

            AnimatedVisibility(visible = isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .animateContentSize()
            ) {
                item { HeaderSection(factureData) }
                item {
                    PaymentAndNumberingSection(
                        factureData, numberingState.data ?: emptyList(), paymentMethods, formatter
                    )
                }
                item { BillingSection(billingData, formatter) }
                item { FactureObservationSection(factureData) }
                item {
                    Text(
                        text = "Cliente",
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                item {
                    CustomerSection(
                        customerData,
                        identificationDocuments,
                        organizations,
                        tributes,
                        locationsState.data ?: emptyList()
                    )
                }
                item {
                    Text(
                        text = "Productos",
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                items(productList) { product -> ProductItem(product) }
                item {
                    Crossfade(targetState = factureState, label = "") { state ->
                        when (state) {
                            is LoginResult.Error -> ErrorText("Error: ${state.message}")
                            is LoginResult.Success -> {
                                LaunchedEffect(Unit) {
                                    Toast.makeText(
                                        context, "Factura creada exitosamente", Toast.LENGTH_SHORT
                                    ).show()
                                    navController.popBackStack()
                                }
                            }

                            else -> Unit
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    ActionButton(enabled = !isLoading, onClick = {
                        factureData.value = factureData.value.copy(
                            customer = customerData.value,
                            billing_period = billingData.value,
                            items = productList
                        )
                        if (validateCustomerData(factureData.value) && isValidFacture(factureData.value)) {
                            factureViewModel.createdFacture(factureData.value)
                        } else {
                            Toast.makeText(
                                context,
                                "¡Por favor, complete todos los campos!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
