package com.factus.app.ui.facture

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.factus.app.domain.models.Customer
import com.factus.app.domain.models.IdentificationDocument
import com.factus.app.domain.models.LegalOrganization
import com.factus.app.domain.models.Location
import com.factus.app.domain.models.TributeClient
import com.factus.app.ui.facture.util.components.FieldName
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IdentificationRow(customerData, identificationDocuments)
            NameAndAddressSection(customerData)
            CompanyAndTradeNameRow(customerData)
            ContactSection(customerData)
            OrganizationAndDvRow(customerData, organizations)
            TributeAndMunicipalityRow(customerData, tributes, locations)
        }
    }
}

@Composable
fun IdentificationRow(
    customerData: MutableState<Customer>, identificationDocuments: List<IdentificationDocument>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            LevelText(text = "IDENTIFICACIÓN")
            FieldName(dataValue = customerData.value.identification, onValueChange = { newId ->
                customerData.value = customerData.value.copy(identification = newId)
            })
        }
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
}

@Composable
fun NameAndAddressSection(
    customerData: MutableState<Customer>
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            LevelText(text = "NOMBRE")
            FieldName(dataValue = customerData.value.names ?: "", onValueChange = { newName ->
                customerData.value = customerData.value.copy(names = newName)
            })
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f),
        ) {
            LevelText(text = "DIRECCIÓN")
            FieldName(dataValue = customerData.value.address ?: "", onValueChange = { newAddress ->
                customerData.value = customerData.value.copy(address = newAddress)
            })
        }
    }

}

@Composable
fun CompanyAndTradeNameRow(
    customerData: MutableState<Customer>
) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (customerData.value.legalOrganizationId == "1") {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                LevelText(text = "EMPRESA")
                FieldName(dataValue = customerData.value.company ?: "",
                    onValueChange = { newCompany ->
                        customerData.value = customerData.value.copy(company = newCompany)
                    })
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        Column(
            modifier = Modifier.weight(1f),
        ) {
            LevelText(text = "NOMBRE COMERCIAL")
            FieldName(dataValue = customerData.value.tradeName ?: "",
                onValueChange = { newTradeName ->
                    customerData.value = customerData.value.copy(tradeName = newTradeName)
                })
        }
    }
}

@Composable
fun ContactSection(
    customerData: MutableState<Customer>
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            LevelText(text = "EMAIL")
            FieldName(dataValue = customerData.value.email ?: "", onValueChange = { newEmail ->
                customerData.value = customerData.value.copy(email = newEmail)
            })
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f),
        ) {
            LevelText(text = "TELÉFONO")
            FieldName(dataValue = customerData.value.phone ?: "", onValueChange = { newPhone ->
                customerData.value = customerData.value.copy(phone = newPhone)
            })
        }
    }
}

@Composable
fun OrganizationAndDvRow(
    customerData: MutableState<Customer>, organizations: List<LegalOrganization>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
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
            Column(
                modifier = Modifier.weight(1f),
            ) {
                LevelText(text = "DV")
                FieldName(dataValue = customerData.value.dv ?: "", onValueChange = { newDv ->
                    customerData.value = customerData.value.copy(dv = newDv)
                })
            }
        }
    }
}

@Composable
fun TributeAndMunicipalityRow(
    customerData: MutableState<Customer>, tributes: List<TributeClient>, locations: List<Location>
) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ListDropdown(label = "TRIBUTO",
            items = tributes,
            selectedItem = tributes.firstOrNull { it.tribute_id == customerData.value.tributeId.toIntOrNull() }
                ?: TributeClient(tribute_id = 0, nombre = "Selecciona"),
            itemToString = { it.nombre },
            modifier = Modifier.weight(1f)
        ) { selected ->
            customerData.value = customerData.value.copy(tributeId = selected.tribute_id.toString())
        }
        ListDropdown(label = "MUNICIPIO",
            items = locations,
            selectedItem = locations.firstOrNull { it.id == customerData.value.municipalityId.toIntOrNull() }
                ?: Location(id = 0, name = "Selecciona", code = "", department = ""),
            itemToString = { it.name },
            modifier = Modifier.weight(1f)
        ) { selected ->
            customerData.value = customerData.value.copy(municipalityId = selected.id.toString())
        }
    }
}
