package util

import androidx.compose.ui.unit.dp

internal actual fun getImageProvider(): ImageProvider =
    ImageProvider(
        columnCount = 4,
        itemHeight = 400.dp
    )
