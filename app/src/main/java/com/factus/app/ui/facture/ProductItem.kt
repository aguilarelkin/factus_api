package com.factus.app.ui.facture

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.factus.app.domain.models.Product

@Composable
fun ProductItem(product: Product) {
    Card(modifier = Modifier.padding(16.dp)) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = product.name,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Precio:", modifier = Modifier.weight(1f))
                Text(text = "$${product.price}")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(text = "Descuento:", modifier = Modifier.weight(1f))
                Text(text = "${product.discountRate}%")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(text = "Impuesto:", modifier = Modifier.weight(1f))
                Text(text = "${product.taxRate}%")
            }
            if (product.withholdingTaxes.isNotEmpty()) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Text(text = "Retenciones:", fontWeight = FontWeight.Medium)
                    product.withholdingTaxes.forEach { tax ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "-${tax.code}")
                            Text(text = "${tax.withholdingTaxRate}%")
                        }
                    }
                }
            } else {
                Text(
                    text = "Sin retenciones", modifier = Modifier.padding(top = 12.dp)
                )
            }
        }
    }
}