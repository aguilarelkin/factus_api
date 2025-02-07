package com.factus.app.domain.models.invoice

import java.io.File

data class FactureItem(
    val id: Int,
    val document: Document?,
    val number: String,
    val apiClientName: String,
    val referenceCode: String,
    val identification: String,
    val graphicRepresentationName: String?,
    val company: String,
    val tradeName: String,
    val names: String,
    val email: String,
    val total: Double,
    val status: Int,
    val errors: List<String>?,
    val sendEmail: Int?,
    val hasClaim: Int?,
    val isNegotiableInstrument: Int?,
    val paymentForm: PaymentForm?,
    val createdAt: String,
    val creditNotes: List<Any>?,
    val debitNotes: List<Any>?,
    var fileUrl :File? = null
)
