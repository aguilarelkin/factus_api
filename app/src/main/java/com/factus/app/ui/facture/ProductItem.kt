package com.factus.app.ui.facture

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.factus.app.domain.models.Product

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .shadow(10.dp, RoundedCornerShape(12.dp))
            .animateContentSize(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 6.dp, pressedElevation = 12.dp
        ),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3EAF2)),
        border = BorderStroke(1.dp, Color(0xFFD0D5DD))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = product.name,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            InfoRow(label = "Cantidad:", value = "${product.quantity}")
            InfoRow(label = "Precio:", value = "$${product.price}")
            InfoRow(label = "Descuento:", value = "${product.discountRate}%")
            InfoRow(label = "Impuesto:", value = "${product.taxRate}%")

            if (product.withholdingTaxes.isNotEmpty()) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = "Retenciones:", fontWeight = FontWeight.Medium
                )
                product.withholdingTaxes.forEach { tax ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "- ${tax.code}"
                        )
                        Text(
                            text = "${tax.withholdingTaxRate}%",
                        )
                    }
                }
            } else {
                Text(
                    text = "Sin retenciones", modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label, modifier = Modifier.weight(1f)
        )
        Text(
            text = value, modifier = Modifier.weight(1f)
        )
    }
}
