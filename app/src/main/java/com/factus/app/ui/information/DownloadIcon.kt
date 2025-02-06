package com.factus.app.ui.information

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val DownloadIcon: ImageVector
    get() {
        if (_downloadIcon != null) return _downloadIcon!!
        _downloadIcon = ImageVector.Builder(
            name = "DownloadIcon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // Ícono de la flecha (shaft y cabeza)
            path(
                fill = SolidColor(Color.Black), pathFillType = PathFillType.NonZero
            ) {
                // Comienza en la parte superior de la flecha
                moveTo(11f, 2f)
                // Línea superior horizontal (ancho de la flecha)
                lineTo(13f, 2f)
                // Línea vertical descendente (shaft de la flecha)
                lineTo(13f, 14f)
                // Brazo derecho de la cabeza
                lineTo(16f, 14f)
                // Punta inferior (vértice central)
                lineTo(12f, 18f)
                // Brazo izquierdo de la cabeza
                lineTo(8f, 14f)
                // Regresa al final del shaft
                lineTo(11f, 14f)
                close()
            }
            // Barra de base del ícono
            path(
                fill = SolidColor(Color.Black), pathFillType = PathFillType.NonZero
            ) {
                moveTo(5f, 20f)
                horizontalLineToRelative(14f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(-14f)
                close()
            }
        }.build()
        return _downloadIcon!!
    }

private var _downloadIcon: ImageVector? = null
