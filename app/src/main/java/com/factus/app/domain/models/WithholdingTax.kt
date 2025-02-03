package com.factus.app.domain.models

import com.factus.app.data.response.WithholdingTaxResponse

data class WithholdingTax(
    val code: String = "", val withholdingTaxRate: String = ""
) {
    fun toDomain() = WithholdingTaxResponse(
        code, withholdingTaxRate
    )
}
