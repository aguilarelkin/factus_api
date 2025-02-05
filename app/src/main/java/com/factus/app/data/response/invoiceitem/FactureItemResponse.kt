package com.factus.app.data.response.invoiceitem

import com.factus.app.domain.models.invoice.FactureItem
import com.google.gson.annotations.SerializedName

data class FactureItemResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("document") val document: DocumentResponse,
    @SerializedName("number") val number: String,
    @SerializedName("api_client_name") val apiClientName: String,
    @SerializedName("reference_code") val referenceCode: String,
    @SerializedName("identification") val identification: String,
    @SerializedName("graphic_representation_name") val graphicRepresentationName: String,
    @SerializedName("company") val company: String,
    @SerializedName("trade_name") val tradeName: String,
    @SerializedName("names") val names: String,
    @SerializedName("email") val email: String,
    @SerializedName("total") val total: String,
    @SerializedName("status") val status: Int,
    @SerializedName("errors") val errors: List<String>,
    @SerializedName("send_email") val sendEmail: Int,
    @SerializedName("has_claim") val hasClaim: Int,
    @SerializedName("is_negotiable_instrument") val isNegotiableInstrument: Int,
    @SerializedName("payment_form") val paymentForm: PaymentFormResponse,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("credit_notes") val creditNotes: List<Any>,
    @SerializedName("debit_notes") val debitNotes: List<Any>
) {
    fun toDomainModel() = FactureItem(
        id,
        document.toDomainModel(),
        number,
        apiClientName,
        referenceCode,
        identification,
        graphicRepresentationName,
        company,
        tradeName,
        names,
        email,
        total.toDouble(),
        status,
        errors,
        sendEmail,
        hasClaim,
        isNegotiableInstrument,
        paymentForm.toDomainModel(),
        createdAt,
        creditNotes,
        debitNotes
    )
}
