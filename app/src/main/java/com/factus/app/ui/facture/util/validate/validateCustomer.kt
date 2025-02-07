package com.factus.app.ui.facture.util.validate

import com.factus.app.domain.models.Facture

fun validateCustomerData(facture: Facture): Boolean {
    if (facture.payment_form == "2" && facture.payment_due_date == null) return false
    if ((facture.customer.identificationDocumentId == 6 || facture.customer.identificationDocumentId == 10) && facture.customer.dv.isNullOrBlank()) return false
    if (facture.customer.legalOrganizationId == "1" && facture.customer.company.isNullOrBlank()) return false
    return true
}