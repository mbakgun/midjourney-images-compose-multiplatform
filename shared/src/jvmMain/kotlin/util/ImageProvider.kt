package util

import androidx.compose.ui.unit.dp

internal actual fun getImageProvider(): ImageProvider =
    ImageProvider(
        columnCount = 3,
        itemHeight = 220.dp
    )
