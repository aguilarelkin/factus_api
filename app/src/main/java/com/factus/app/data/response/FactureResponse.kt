package com.factus.app.data.response

import com.factus.app.domain.models.Facture
import com.google.gson.annotations.SerializedName

data class FactureResponse(
    @SerializedName("numbering_range_id") val numbering_range_id: Int = 0,
    @SerializedName("reference_code") val reference_code: String = "",
    @SerializedName("observation") val observation: String = "",
    @SerializedName("payment_form") val payment_form: String = "1",
    @SerializedName("payment_due_date") val payment_due_date: String? = null,
    @SerializedName("payment_method_code") val payment_method_code: String = "10",
    @SerializedName("billing_period") val billing_period: BillingPeriodResponse? = null,
    @SerializedName("customer") val customer: CustomerResponse = CustomerResponse(),
    @SerializedName("items") val items: List<ProductResponse> = listOf()
) {
    fun toDomain() = Facture(numbering_range_id,
        reference_code,
        observation,
        payment_form,
        payment_due_date,
        payment_method_code,
        billing_period?.toDomain(),
        customer.toDomain(),
        items.map {
            it.toDomain()
        })
}
