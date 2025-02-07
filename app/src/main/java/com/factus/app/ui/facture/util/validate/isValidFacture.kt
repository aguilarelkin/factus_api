package com.factus.app.ui.facture.util.validate

import com.factus.app.domain.models.Facture

fun isValidFacture(facture: Facture): Boolean {
    if (facture.numbering_range_id <= 0) return false
    if (facture.reference_code.isBlank()) return false
    if (facture.payment_form.isBlank()) return false
    if (facture.payment_method_code.isBlank()) return false
    val bp = facture.billing_period ?: return false
    if (bp.start_date == null) return false
    if (bp.start_time.isNullOrBlank()) return false
    if (bp.end_date == null) return false
    if (bp.end_time.isNullOrBlank()) return false
    val customer = facture.customer
    if (customer.identification.isBlank()) return false
    if (customer.names?.isBlank() == true) return false
    if (customer.address?.isBlank() == true) return false
    if (customer.email?.isBlank() == true) return false
    if (customer.phone?.isBlank() == true) return false
    if (customer.legalOrganizationId?.isBlank() == true) return false
    if (customer.tributeId.isBlank()) return false
    if (customer.identificationDocumentId == 0) return false
    if (customer.municipalityId.isBlank()) return false
    if (facture.items.isEmpty()) return false
    facture.items.forEach { product ->
        if (product.name.isBlank()) return false
    }
    return true
}