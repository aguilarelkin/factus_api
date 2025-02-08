package com.factus.app.ui.facture

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.factus.app.domain.models.Facture
import com.factus.app.domain.models.Numbering
import com.factus.app.domain.models.Payment
import com.factus.app.domain.models.PaymentMethodCode
import com.factus.app.ui.facture.util.components.ListDropdown
import com.factus.app.ui.facture.util.list.getFormPayment
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun PaymentAndNumberingSection(
    factureData: MutableState<Facture>,
    numberingData: List<Numbering>,
    paymentMethods: List<PaymentMethodCode>,
    formatter: DateTimeFormatter
) {
    val formPayments = getFormPayment()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            AnimatedVisibility(visible = factureData.value.payment_form == "2") {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DatePickerField(label = "FECHA DE VENCIMIENTO",
                        selectedDate = factureData.value.payment_due_date ?: "",
                        onDateSelected = { dateStr ->
                            try {
                                val parsedDate = LocalDate.parse(dateStr, formatter)
                                factureData.value =
                                    factureData.value.copy(payment_due_date = parsedDate.toString())
                            } catch (e: Exception) {
                                // Manejo de error
                            }
                        })
                }

            }

            ListDropdown(label = "RANGO DE NUMERACIÓN",
                items = numberingData,
                selectedItem = numberingData.firstOrNull { it.id == factureData.value.numbering_range_id }
                    ?: Numbering(id = 0, document = "Selecciona un rango"),
                itemToString = { it.document },
                modifier = Modifier.fillMaxWidth(),
                onItemSelected = { selected ->
                    factureData.value = factureData.value.copy(numbering_range_id = selected.id)
                })

            PaymentMethodSelectionRow(factureData, formPayments, paymentMethods)
        }
    }
}

@Composable
fun PaymentMethodSelectionRow(
    factureData: MutableState<Facture>,
    formPayments: List<Payment>,
    paymentMethods: List<PaymentMethodCode>
) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ListDropdown(label = "FORMA DE PAGO",
            items = formPayments,
            selectedItem = formPayments.firstOrNull { it.id == factureData.value.payment_form.toIntOrNull() }
                ?: Payment(id = 0, name = "Selecciona"),
            itemToString = { it.name },
            modifier = Modifier.weight(1f),
            onItemSelected = { selected ->
                factureData.value = factureData.value.copy(payment_form = selected.id.toString())
            })
        ListDropdown(label = "CÓDIGO DE PAGO",
            items = paymentMethods,
            selectedItem = paymentMethods.firstOrNull { it.code == factureData.value.payment_method_code.toIntOrNull() }
                ?: PaymentMethodCode(code = 0, description = "Selecciona"),
            itemToString = { it.description },
            modifier = Modifier.weight(1f),
            onItemSelected = { selected ->
                factureData.value =
                    factureData.value.copy(payment_method_code = selected.code.toString())
            })
    }
}

