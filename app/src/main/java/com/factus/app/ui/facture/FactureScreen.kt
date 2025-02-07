package ui.facture

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.factus.app.domain.models.BillingPeriod
import com.factus.app.domain.models.Customer
import com.factus.app.domain.models.CustomerSaver
import com.factus.app.domain.models.Facture
import com.factus.app.domain.state.LoginResult
import com.factus.app.ui.facture.BillingSection
import com.factus.app.ui.facture.CustomerSection
import com.factus.app.ui.facture.FactureObservationSection
import com.factus.app.ui.facture.FactureTopBar
import com.factus.app.ui.facture.FactureViewModel
import com.factus.app.ui.facture.HeaderSection
import com.factus.app.ui.facture.PaymentAndNumberingSection
import com.factus.app.ui.facture.ProductItem
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
    factureViewModel: FactureViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Scaffold(topBar = { FactureTopBar(onBackClick = { navController.popBackStack() }) }) { innerPadding ->
        val numberingState by factureViewModel.numberingRangesState.collectAsState()
        val factureState by factureViewModel.factureState.collectAsState()
        val locationsState by factureViewModel.locationsState.collectAsState()

        val customerData =
            rememberSaveable(stateSaver = CustomerSaver) { mutableStateOf(Customer()) }
        val billingData = remember { mutableStateOf(BillingPeriod()) }
        val factureData = remember { mutableStateOf(Facture()) }

        val paymentMethods = getPaymentMethods()
        val identificationDocuments = getIdentificationDocuments()
        val organizations = getOrganizations()
        val tributes = getTributes()
        val productList = getProductList()

        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy")
        val context = LocalContext.current

        LazyColumn(
            contentPadding = innerPadding,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item { HeaderSection(factureData) }

            item {
                PaymentAndNumberingSection(
                    factureData = factureData,
                    numberingData = numberingState.data ?: emptyList(),
                    paymentMethods = paymentMethods,
                    formatter = formatter
                )
            }

            item { BillingSection(billingData, formatter) }

            item {
                FactureObservationSection(factureData = factureData)
            }

            item {
                CustomerSection(
                    customerData = customerData,
                    identificationDocuments = identificationDocuments,
                    organizations = organizations,
                    tributes = tributes,
                    locations = locationsState.data ?: emptyList()
                )
            }

            items(productList) { product ->
                ProductItem(product = product)
            }

            when (factureState) {
                is LoginResult.Error -> {
                    val errorMessage = (factureState as LoginResult.Error<*>).message
                    item { ErrorText(message = "Error: $errorMessage") }
                }

                is LoginResult.Loading -> {
                    val isLoading = (factureState as LoginResult.Loading<*>).isLoading
                    if (isLoading) item { CircularProgressIndicator() }
                }

                is LoginResult.Success -> {
                    item {
                        LaunchedEffect(Unit) {
                            Toast.makeText(
                                context, "Factura creada exitosamente", Toast.LENGTH_SHORT
                            ).show()
                            navController.popBackStack()
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                ActionButton(enabled = true, onClick = {
                    factureData.value = factureData.value.copy(
                        customer = customerData.value,
                        billing_period = billingData.value,
                        items = productList
                    )
                    Log.e("adfa", factureData.value.toString())
                    val isValid =
                        validateCustomerData(factureData.value) && isValidFacture(factureData.value)
                    if (isValid) {
                        factureViewModel.createdFacture(factureData.value)

                    } else {
                        Toast.makeText(
                            context,
                            "¡Por favor, complete todos los campos necesarios!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
