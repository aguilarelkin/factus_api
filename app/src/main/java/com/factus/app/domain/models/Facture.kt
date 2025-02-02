package com.factus.app.domain.models

import java.time.LocalDate

data class Facture(
    val numbering_range_id: Int = 0,
    val reference_code: String = "",
    val observation: String = "",
    val payment_form: String = "1",
    val payment_due_date: LocalDate? = null,
    val payment_method_code: String = "10",
    val billing_period: BillingPeriod? = null,
    val customer: Customer = Customer(),
    val items: List<Product> = listOf()
)
