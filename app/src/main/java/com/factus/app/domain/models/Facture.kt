package com.factus.app.domain.models

import java.time.LocalDate

data class Facture(
    val numbering_range_id: Int,
    val reference_code: String,
    val observation: String,
    val payment_form: String,
    val payment_due_date: LocalDate,
    val payment_method_code: String,
    val billing_period: BillingPeriod,
    val customer: Customer,
    val items: List<Product>
)
