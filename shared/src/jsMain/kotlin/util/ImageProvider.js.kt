package util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal actual fun getImageProvider(width: Dp): ImageProvider {
    val columnCount = fromWidth(width)
    return ImageProvider(
        columnCount = columnCount,
        itemHeight = getItemHeightByColumn(columnCount)
    )
}

private fun getItemHeightByColumn(columnCount: Int) = when (columnCount) {
    2 -> 180.dp
    3 -> 300.dp
    4 -> 400.dp
    else -> throw IllegalArgumentException("Unsupported column count: $columnCount")
}

fun fromWidth(width: Dp): Int {
    return if (width < 1080.dp) {
        2
    } else if (width < 1440.dp) {
        3
    } else {
        4
    }
}
