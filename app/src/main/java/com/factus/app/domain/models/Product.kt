package com.factus.app.domain.models

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
)
