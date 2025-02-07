package com.factus.app.ui.facture

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.factus.app.domain.models.Facture
import com.factus.app.domain.models.Numbering
import com.factus.app.domain.models.Payment
import com.factus.app.domain.models.PaymentMethodCode
import com.factus.app.ui.facture.util.components.FormDatePicker
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            if (factureData.value.payment_form == "2") {
                FormDatePicker(label = "FECHA DE VENCIMIENTO",
                    value = factureData.value.payment_due_date ?: "",
                    modifier = Modifier.weight(1f),
                    onValueChange = { dateStr ->
                        val parsedDate = LocalDate.parse(dateStr, formatter)
                        factureData.value =
                            factureData.value.copy(payment_due_date = parsedDate.toString())
                    })
                Spacer(modifier = Modifier.width(8.dp))
            }
            ListDropdown(label = "RANGO DE NUMERACIÓN",
                items = numberingData,
                selectedItem = numberingData.firstOrNull { it.id == factureData.value.numbering_range_id }
                    ?: Numbering(id = 0, document = "Selecciona un rango"),
                itemToString = { it.document },
                modifier = Modifier.weight(1f),
                onItemSelected = { selected ->
                    factureData.value = factureData.value.copy(numbering_range_id = selected.id)
                })
        }
        Row(modifier = Modifier.fillMaxWidth()) {

            ListDropdown(label = "FORMA DE PAGO",
                items = getFormPayment(),
                selectedItem = getFormPayment().firstOrNull { it.id == factureData.value.payment_form.toIntOrNull() }
                    ?: Payment(id = 0, name = "Selecciona"),
                itemToString = { it.name },
                modifier = Modifier.weight(1f),
                onItemSelected = { selected ->
                    factureData.value =
                        factureData.value.copy(payment_form = selected.id.toString())
                })
            Spacer(modifier = Modifier.width(8.dp))
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
}