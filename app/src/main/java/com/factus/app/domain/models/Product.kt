package com.factus.app.domain.models

data class Product(
    val codeReference: String,
    val name: String,
    val quantity: Int,
    val discountRate: Int,
    val price: Int,
    val taxRate: String,
    val unitMeasureId: Int,
    val standardCodeId: Int,
    val isExcluded: Int,
    val tributeId: Int,
    val withholdingTaxes: List<WithholdingTax>
)
