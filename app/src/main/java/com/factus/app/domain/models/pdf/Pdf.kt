package com.factus.app.domain.models.pdf

data class Pdf(
    val status: String,
    val message: String,
    val data: PdfData?
)
