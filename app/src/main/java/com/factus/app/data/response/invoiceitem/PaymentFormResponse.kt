package com.factus.app.data.response.invoiceitem

import com.factus.app.domain.models.invoice.PaymentForm
import com.google.gson.annotations.SerializedName

data class PaymentFormResponse(
    @SerializedName("code") val code: String, @SerializedName("name") val name: String
) {
    fun toDomainModel() = PaymentForm(code, name)
}
