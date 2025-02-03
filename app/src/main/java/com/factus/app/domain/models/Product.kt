package com.factus.app.domain.models

import com.factus.app.data.response.ProductResponse

data class Product(
    val codeReference: String = "",
    val name: String = "",
    val quantity: Int = 0,
    val discountRate: Int = 0,
    val price: Int = 0,
    val taxRate: String = "",
    val unitMeasureId: Int = 0,
    val standardCodeId: Int = 0,
    val isExcluded: Int = 0,
    val tributeId: Int = 0,
    val withholdingTaxes: List<WithholdingTax> = listOf()
) {
    fun toDomain() = ProductResponse(codeReference,
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
