package com.factus.app.domain.models

import androidx.compose.runtime.saveable.Saver

data class Customer(
    val identification: String = "",
    val dv: String? = null,
    val company: String? = null,
    val tradeName: String? = null,
    val names: String? = null,
    val address: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val legalOrganizationId: String? = null,
    val tributeId: String = "",
    val identificationDocumentId: String = "",
    val municipalityId: String = ""
)

val CustomerSaver = Saver<Customer, Map<String, String?>>(save = { customer ->
    mapOf(
        "identification" to customer.identification,
        "dv" to customer.dv,
        "company" to customer.company,
        "tradeName" to customer.tradeName,
        "names" to customer.names,
        "address" to customer.address,
        "email" to customer.email,
        "phone" to customer.phone,
        "legalOrganizationId" to customer.legalOrganizationId,
        "tributeId" to customer.tributeId,
        "identificationDocumentId" to customer.identificationDocumentId,
        "municipalityId" to customer.municipalityId
    )
}, restore = { savedState ->
    Customer(
        identification = savedState["identification"] ?: "",
        dv = savedState["dv"] ?: "",
        company = savedState["company"] ?: "",
        tradeName = savedState["tradeName"] ?: "",
        names = savedState["names"] ?: "",
        address = savedState["address"] ?: "",
        email = savedState["email"] ?: "",
        phone = savedState["phone"] ?: "",
        legalOrganizationId = savedState["legalOrganizationId"] ?: "",
        tributeId = savedState["tributeId"] ?: "",
        identificationDocumentId = savedState["identificationDocumentId"] ?: "",
        municipalityId = savedState["municipalityId"] ?: ""
    )
})