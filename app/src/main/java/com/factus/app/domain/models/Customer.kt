package com.factus.app.domain.models

import androidx.compose.runtime.saveable.Saver
import com.factus.app.data.response.CustomerResponse

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
    val identificationDocumentId: Int = 0,
    val municipalityId: String = ""
) {
    fun toDomain() = CustomerResponse(
        identification,
        dv,
        company,
        tradeName,
        names,
        address,
        email,
        phone,
        legalOrganizationId,
        tributeId,
        identificationDocumentId,
        municipalityId
    )
}

val CustomerSaver = Saver<Customer, Map<String, Any?>>(save = { customer ->
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
        identification = savedState["identification"] as? String ?: "",
        dv = savedState["dv"] as? String,
        company = savedState["company"] as? String,
        tradeName = savedState["tradeName"] as? String,
        names = savedState["names"] as? String,
        address = savedState["address"] as? String,
        email = savedState["email"] as? String,
        phone = savedState["phone"] as? String,
        legalOrganizationId = savedState["legalOrganizationId"] as? String,
        tributeId = savedState["tributeId"] as? String ?: "",
        identificationDocumentId = savedState["identificationDocumentId"] as? Int ?: 0,
        municipalityId = savedState["municipalityId"] as? String ?: ""
    )
})