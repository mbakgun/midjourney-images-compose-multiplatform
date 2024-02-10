package util

import androidx.compose.ui.unit.dp

internal actual fun getImageProvider(): ImageProvider =
    ImageProvider(
        columnCount = 2,
        itemHeight = 180.dp
    )