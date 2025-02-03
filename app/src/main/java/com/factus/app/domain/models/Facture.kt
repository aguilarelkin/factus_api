package com.factus.app.domain.models

import com.factus.app.data.response.FactureResponse
import java.time.LocalDate

data class Facture(
    val numbering_range_id: Int = 0,
    val reference_code: String = "",
    val observation: String = "",
    val payment_form: String = "1",
    val payment_due_date: String? = null,
    val payment_method_code: String = "10",
    val billing_period: BillingPeriod? = null,
    val customer: Customer = Customer(),
    val items: List<Product> = listOf()
) {
    fun toDomain() = FactureResponse(numbering_range_id,
        reference_code,
        observation,
        payment_form,
        payment_due_date,
        payment_method_code,
        billing_period?.toDomain(),
        customer.toDomain(),
        items.map { it.toDomain() })
}
