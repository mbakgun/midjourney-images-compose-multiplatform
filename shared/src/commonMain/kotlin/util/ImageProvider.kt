package util

import androidx.compose.ui.unit.Dp

data class ImageProvider(val columnCount: Int, val itemHeight: Dp)

internal expect fun getImageProvider(width: Dp): ImageProvider
