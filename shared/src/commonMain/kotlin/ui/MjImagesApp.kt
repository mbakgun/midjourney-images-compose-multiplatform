package ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.ImageRequestState
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.rememberAsyncImagePainter
import domain.model.MjImage
import domain.model.MjImages
import domain.model.State
import util.generateImageLoader

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MjImagesApp(
    viewModel: MjImagesViewModel
) {
    CompositionLocalProvider(
        LocalImageLoader provides generateImageLoader()
    ) {
        val images: MjImages by viewModel.images.collectAsState()
        val state: State by viewModel.state.collectAsState()
        val onRefresh = { viewModel.refreshImages() }
        val onLoadMore: () -> Unit = { viewModel.loadMore() }

        MaterialTheme {
            val isRefreshing = state == State.LOADING
            val pullRefreshState = rememberPullRefreshState(isRefreshing, { onRefresh() })

            Box(
                Modifier
                    .fillMaxSize()
                    .pullRefresh(state = pullRefreshState)
            ) {
                when (state) {
                    State.ERROR -> ErrorScreen(onRefresh)
                    State.EMPTY -> EmptyScreen(onRefresh)
                    else -> MjImagesList(
                        onLoadMore,
                        images,
                    )
                }

                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter).testTag("pullRefreshIndicator")
                )
            }
        }
    }
}

@Composable
fun MjImagesList(onLoadMore: () -> Unit, images: MjImages) {
    PlatformSpecificMjImagesGrid(
        onLoadMore = onLoadMore,
        images = images,
        modifier = Modifier.fillMaxSize().testTag("imagesGrid")
    )
}

// for now, unfortunately we need don't have LazyStaggeredGridMeasureResult in iOS Compose,
// we need to use LazyGrid instead
@Composable
expect fun PlatformSpecificMjImagesGrid(
    onLoadMore: () -> Unit,
    images: MjImages,
    modifier: Modifier = Modifier,
)

@Composable
fun MjImageItem(
    image: MjImage,
    height: Dp,
    contentScale: ContentScale,
) {
    val uriHandler = LocalUriHandler.current

    Surface(
        modifier = Modifier.padding(4.dp).fillMaxWidth().clickable(
            onClick = { uriHandler.openUri(image.imageUrl) },
        ), elevation = 8.dp, shape = RoundedCornerShape(8.dp)
    ) {
        val painter = rememberAsyncImagePainter(
            image.imageUrl, contentScale = contentScale
        )
        val imageRequestState = painter.requestState

        val transition by animateFloatAsState(
            targetValue = if (imageRequestState is ImageRequestState.Success) 1f else 0f
        )

        Image(painter, contentDescription = null, modifier = Modifier.graphicsLayer {
            val animatedValue: Float = .8f + (.2f * transition)
            val blurValue: Float = if (transition == 1f) 1f else 24f + (.2f * transition)
            scaleX = animatedValue
            scaleY = animatedValue
            alpha = animatedValue
            renderEffect = BlurEffect(blurValue, blurValue)
        }.height(height), contentScale = contentScale)
    }
}

@Composable
fun EmptyScreen(
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No images",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(8.dp),
        )

        Text(
            text = "Please try again later",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(8.dp),
        )

        Button(
            onClick = { onRefresh() }, modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Refresh")
        }
    }
}

@Composable
fun ErrorScreen(
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Error",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(8.dp),
        )

        Text(
            text = "Please try again later",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(8.dp),
        )

        Button(
            onClick = { onRefresh() }, modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Refresh")
        }
    }
}
