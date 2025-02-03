package com.factus.app.data.response

import com.factus.app.domain.models.Customer
import com.google.gson.annotations.SerializedName

data class CustomerResponse(
    @SerializedName("identification") val identification: String = "",
    @SerializedName("dv") val dv: String? = null,
    @SerializedName("company") val company: String? = null,
    @SerializedName("trade_name") val tradeName: String? = null,
    @SerializedName("names") val names: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("legal_organization_id") val legalOrganizationId: String? = null,
    @SerializedName("tribute_id") val tributeId: String = "",
    @SerializedName("identification_document_id") val identificationDocumentId: Int = 0,
    @SerializedName("municipality_id") val municipalityId: String = ""
) {
    fun toDomain() = Customer(
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