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
import com.factus.app.domain.models.Customer
import com.factus.app.domain.models.IdentificationDocument
import com.factus.app.domain.models.LegalOrganization
import com.factus.app.domain.models.Location
import com.factus.app.domain.models.TributeClient
import com.factus.app.ui.facture.util.components.FormColumn
import com.factus.app.ui.facture.util.components.FormSection
import com.factus.app.ui.facture.util.components.LevelText
import com.factus.app.ui.facture.util.components.ListDropdown

@Composable
fun CustomerSection(
    customerData: MutableState<Customer>,
    identificationDocuments: List<IdentificationDocument>,
    organizations: List<LegalOrganization>,
    tributes: List<TributeClient>,
    locations: List<Location>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        LevelText("Cliente")
        Row(modifier = Modifier.fillMaxWidth()) {
            FormColumn(
                label = "IDENTIFICACIÓN",
                value = customerData.value.identification,
                modifier = Modifier.weight(1f)
            ) { newId ->
                customerData.value = customerData.value.copy(identification = newId)
            }
            Spacer(modifier = Modifier.width(8.dp))
            ListDropdown(label = "ID DOCUMENTO",
                items = identificationDocuments,
                selectedItem = identificationDocuments.firstOrNull { it.id == customerData.value.identificationDocumentId }
                    ?: IdentificationDocument(id = 0, name = "Selecciona"),
                itemToString = { it.name },
                modifier = Modifier.weight(1f)
            ) { selected ->
                customerData.value = customerData.value.copy(identificationDocumentId = selected.id)
            }
        }
        FormSection(label1 = "NOMBRE",
            value1 = customerData.value.names,
            onValueChange1 = { newName ->
                customerData.value = customerData.value.copy(names = newName)
            },
            label2 = "DIRECCIÓN",
            value2 = customerData.value.address,
            onValueChange2 = { newAddress ->
                customerData.value = customerData.value.copy(address = newAddress)
            })
        Row(modifier = Modifier.fillMaxWidth()) {
            if (customerData.value.legalOrganizationId == "1") {
                FormColumn(
                    label = "EMPRESA",
                    value = customerData.value.company,
                    modifier = Modifier.weight(1f)
                ) { newCompany ->
                    customerData.value = customerData.value.copy(company = newCompany)
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            FormColumn(
                label = "NOMBRE COMERCIAL",
                value = customerData.value.tradeName,
                modifier = Modifier.weight(1f)
            ) { newTradeName ->
                customerData.value = customerData.value.copy(tradeName = newTradeName)
            }
        }
        FormSection(label1 = "EMAIL",
            value1 = customerData.value.email,
            onValueChange1 = { newEmail ->
                customerData.value = customerData.value.copy(email = newEmail)
            },
            label2 = "TELÉFONO",
            value2 = customerData.value.phone,
            onValueChange2 = { newPhone ->
                customerData.value = customerData.value.copy(phone = newPhone)
            })
        Row(modifier = Modifier.fillMaxWidth()) {
            ListDropdown(label = "ORGANIZACIÓN LEGAL",
                items = organizations,
                selectedItem = organizations.firstOrNull { it.legal_organization_id == customerData.value.legalOrganizationId?.toIntOrNull() }
                    ?: LegalOrganization(legal_organization_id = 0, nombre = "Selecciona"),
                itemToString = { it.nombre },
                modifier = Modifier.weight(1f)
            ) { selected ->
                customerData.value =
                    customerData.value.copy(legalOrganizationId = selected.legal_organization_id.toString())
            }
            Spacer(modifier = Modifier.width(8.dp))
            if (customerData.value.identificationDocumentId == 10 || customerData.value.identificationDocumentId == 6) {
                FormColumn(
                    label = "DV", value = customerData.value.dv, modifier = Modifier.weight(1f)
                ) { newDv ->
                    customerData.value = customerData.value.copy(dv = newDv)
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            ListDropdown(label = "TRIBUTO",
                items = tributes,
                selectedItem = tributes.firstOrNull { it.tribute_id == customerData.value.tributeId.toIntOrNull() }
                    ?: TributeClient(tribute_id = 0, nombre = "Selecciona"),
                itemToString = { it.nombre },
                modifier = Modifier.weight(1f)
            ) { selected ->
                customerData.value =
                    customerData.value.copy(tributeId = selected.tribute_id.toString())
            }
            Spacer(modifier = Modifier.width(8.dp))
            ListDropdown(label = "MUNICIPALIDAD",
                items = locations,
                selectedItem = locations.firstOrNull { it.id == customerData.value.municipalityId.toIntOrNull() }
                    ?: Location(id = 0, name = "", code = "", department = "Selecciona"),
                itemToString = { it.name },
                modifier = Modifier.weight(1f)
            ) { selected ->
                customerData.value =
                    customerData.value.copy(municipalityId = selected.id.toString())
            }
        }
    }
}