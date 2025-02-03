package com.factus.app.data.response

import com.factus.app.domain.models.Product
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("code_reference") val codeReference: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("quantity") val quantity: Int = 0,
    @SerializedName("discount_rate") val discountRate: Int = 0,
    @SerializedName("price") val price: Int = 0,
    @SerializedName("tax_rate") val taxRate: String = "",
    @SerializedName("unit_measure_id") val unitMeasureId: Int = 0,
    @SerializedName("standard_code_id") val standardCodeId: Int = 0,
    @SerializedName("is_excluded") val isExcluded: Int = 0,
    @SerializedName("tribute_id") val tributeId: Int = 0,
    @SerializedName("withholding_taxes") val withholdingTaxes: List<WithholdingTaxResponse> = listOf()
) {
    fun toDomain() = Product(codeReference,
        name,
        quantity,
        discountRate,
        price,
        taxRate,
        unitMeasureId,
        standardCodeId,
        isExcluded,
        tributeId,
        withholdingTaxes.map { it.toDomain() })
}
