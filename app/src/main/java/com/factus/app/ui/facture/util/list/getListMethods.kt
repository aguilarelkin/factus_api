package com.factus.app.ui.facture.util.list

import com.factus.app.domain.models.IdentificationDocument
import com.factus.app.domain.models.LegalOrganization
import com.factus.app.domain.models.Payment
import com.factus.app.domain.models.PaymentMethodCode
import com.factus.app.domain.models.Product
import com.factus.app.domain.models.TributeClient
import com.factus.app.domain.models.WithholdingTax

fun getFormPayment(): List<Payment> {
    return listOf(
        Payment(id = 1, name = "Contado"), Payment(id = 2, name = "Crédito")
    )
}

fun getPaymentMethods(): List<PaymentMethodCode> {
    return listOf(
        PaymentMethodCode(10, "Efectivo"),
        PaymentMethodCode(42, "Consignación"),
        PaymentMethodCode(20, "Cheque"),
        PaymentMethodCode(47, "Transferencia"),
        PaymentMethodCode(71, "Bonos"),
        PaymentMethodCode(72, "Vales"),
        PaymentMethodCode(1, "Medio de pago no definido"),
        PaymentMethodCode(49, "Tarjeta Débito"),
        PaymentMethodCode(48, "Tarjeta Crédito")
    )
}

fun getIdentificationDocuments(): List<IdentificationDocument> {
    return listOf(
        IdentificationDocument(1, "Registro civil"),
        IdentificationDocument(2, "Tarjeta de identidad"),
        IdentificationDocument(3, "Cédula de ciudadanía"),
        IdentificationDocument(4, "Tarjeta de extranjería"),
        IdentificationDocument(5, "Cédula de extranjería"),
        IdentificationDocument(6, "NIT"),
        IdentificationDocument(7, "Pasaporte"),
        IdentificationDocument(8, "Documento de identificación extranjero"),
        IdentificationDocument(9, "PEP"),
        IdentificationDocument(10, "NIT otro país"),
        IdentificationDocument(11, "NUIP")
    )
}

fun getOrganizations(): List<LegalOrganization> {
    return listOf(
        LegalOrganization(1, "Persona Jurídica"), LegalOrganization(2, "Persona Natural")
    )
}

fun getTributes(): List<TributeClient> {
    return listOf(
        TributeClient(18, "IVA"), TributeClient(21, "No aplica")
    )
}

fun getProductList(): List<Product> {
    return listOf(
        Product(
            codeReference = "12345",
            name = "Laptop Dell XPS 13",
            quantity = 1,
            discountRate = 20,
            price = 1500000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "06", withholdingTaxRate = "7.00"), // ReteRenta
                WithholdingTax(code = "05", withholdingTaxRate = "15.00") // ReteIVA
            )
        ), Product(
            codeReference = "67890",
            name = "Smartphone Samsung Galaxy S21",
            quantity = 2,
            discountRate = 10,
            price = 900000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = emptyList()
        ), Product(
            codeReference = "11223",
            name = "Tablet Apple iPad Pro",
            quantity = 3,
            discountRate = 15,
            price = 1200000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "06", withholdingTaxRate = "7.00") // ReteRenta
            )
        ), Product(
            codeReference = "44556",
            name = "Auriculares Bose QuietComfort",
            quantity = 4,
            discountRate = 25,
            price = 350000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "06", withholdingTaxRate = "10.00") // ReteICA
            )
        ), Product(
            codeReference = "78901",
            name = "Reloj Casio G-Shock",
            quantity = 5,
            discountRate = 30,
            price = 500000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = emptyList()
        ), Product(
            codeReference = "23456",
            name = "Impresora HP LaserJet",
            quantity = 6,
            discountRate = 5,
            price = 800000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "05", withholdingTaxRate = "15.00")
            )
        ), Product(
            codeReference = "34567",
            name = "Monitor LG UltraWide",
            quantity = 7,
            discountRate = 20,
            price = 600000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "06", withholdingTaxRate = "8.00") // ReteRenta
            )
        ), Product(
            codeReference = "45678",
            name = "Teclado Logitech MX Keys",
            quantity = 8,
            discountRate = 10,
            price = 250000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = emptyList()
        ), Product(
            codeReference = "56789",
            name = "Mouse Microsoft Arc",
            quantity = 9,
            discountRate = 15,
            price = 150000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "05", withholdingTaxRate = "14.00") // ReteIVA
            )
        ), Product(
            codeReference = "67801",
            name = "Cámara Canon EOS Rebel",
            quantity = 10,
            discountRate = 0,
            price = 2000000,
            taxRate = "19.00",
            unitMeasureId = 70,
            standardCodeId = 1,
            isExcluded = 0,
            tributeId = 1,
            withholdingTaxes = listOf(
                WithholdingTax(code = "06", withholdingTaxRate = "9.00") // ReteICA
            )
        )
    )
}
