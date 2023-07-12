package ui

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import domain.model.MjImage
import domain.model.MjImages
import util.OnBottomReached

@Composable
actual fun PlatformSpecificMjImagesGrid(
    onLoadMore: () -> Unit,
    images: MjImages,
    modifier: Modifier,
    onPreviewVisibilityChanged: @Composable (isVisible: Boolean, imageUrl: String) -> Unit,
) {
    LazyVerticalGrid(
        state = rememberLazyGridState().apply {
            OnBottomReached(onLoadMore::invoke)
        },
        columns = GridCells.Fixed(2),
        modifier = modifier,
    ) {
        items(
            items = images.images,
            key = MjImage::imageUrl
        ) { image ->
            MjImageItem(
                image,
                300.dp,
                ContentScale.Crop,
                onPreviewVisibilityChanged
            )
        }
    }
}
