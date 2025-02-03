package com.factus.app.data.response

import com.factus.app.domain.models.WithholdingTax
import com.google.gson.annotations.SerializedName

data class WithholdingTaxResponse(
    @SerializedName("code") val code: String = "",
    @SerializedName("withholding_tax_rate") val withholdingTaxRate: String = ""
) {
    fun toDomain() = WithholdingTax(code, withholdingTaxRate)
}
