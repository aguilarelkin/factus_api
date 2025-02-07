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
            path(
                fill = SolidColor(Color.Black), pathFillType = PathFillType.NonZero
            ) {
                moveTo(11f, 2f)
                lineTo(13f, 2f)
                lineTo(13f, 14f)
                lineTo(16f, 14f)
                lineTo(12f, 18f)
                lineTo(8f, 14f)
                lineTo(11f, 14f)
                close()
            }
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
