package util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal actual fun getImageProvider(width: Dp): ImageProvider =
    ImageProvider(
        columnCount = 3,
        itemHeight = 220.dp
    )
