package ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.model.ImageResult
import com.seiko.imageloader.rememberImageAction
import com.seiko.imageloader.rememberImageActionPainter
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogProperties
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import domain.model.MjImage
import domain.model.MjImages
import domain.model.State
import kotlinx.coroutines.delay
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
        val onRefresh = viewModel::refreshImages

        MaterialTheme {
            val scaffoldState: ScaffoldState = rememberScaffoldState()

            LaunchedEffect(Unit) {
                if (viewModel.isEligibleToShowSnackBar()) {
                    scaffoldState.snackbarHostState.showSnackbar(SNACK_MESSAGE)
                    viewModel.setSnackMessageShown()
                }
            }

            Scaffold(scaffoldState = scaffoldState) {
                val isRefreshing = state == State.LOADING
                val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh)

                Box(
                    Modifier
                        .fillMaxSize()
                        .pullRefresh(state = pullRefreshState)
                ) {
                    when (state) {
                        State.ERROR -> ErrorScreen(onRefresh)
                        State.EMPTY -> EmptyScreen(onRefresh)
                        else -> MjImagesList(
                            viewModel::loadMore,
                            images,
                        ) { isPreviewVisible, imageUrl ->
                            PreviewDialog(isPreviewVisible, imageUrl)
                        }
                    }
                    PullRefreshIndicator(
                        refreshing = isRefreshing,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter)
                            .testTag("pullRefreshIndicator")
                    )
                }
            }
        }
    }
}

@Composable
fun MjImagesList(
    onLoadMore: () -> Unit,
    images: MjImages,
    onPreviewVisibilityChanged: @Composable (isVisible: Boolean, imageUrl: String) -> Unit,
) {
    PlatformSpecificMjImagesGrid(
        onLoadMore = onLoadMore,
        images = images,
        modifier = Modifier.fillMaxSize().testTag("imagesGrid"),
        onPreviewVisibilityChanged = onPreviewVisibilityChanged
    )
}

// for now, unfortunately we need don't have LazyStaggeredGridMeasureResult in iOS Compose,
// we need to use LazyGrid instead
@Composable
expect fun PlatformSpecificMjImagesGrid(
    onLoadMore: () -> Unit,
    images: MjImages,
    modifier: Modifier = Modifier,
    onPreviewVisibilityChanged: @Composable (isVisible: Boolean, imageUrl: String) -> Unit,
)

@Composable
fun MjImageItem(
    image: MjImage,
    height: Dp,
    contentScale: ContentScale,
    onPreviewVisibilityChanged: @Composable (isVisible: Boolean, imageUrl: String) -> Unit,
) {
    val uriHandler = LocalUriHandler.current
    var isLongPressed by remember { mutableStateOf(false) }

    if (isLongPressed) {
        onPreviewVisibilityChanged.invoke(true, image.imageUrl)
    } else {
        onPreviewVisibilityChanged.invoke(false, image.imageUrl)
    }

    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (!isLongPressed)
                            uriHandler.openUri(image.imageUrl)
                    },
                    onPress = {
                        delay(200)
                        isLongPressed = true
                        tryAwaitRelease()
                        isLongPressed = false
                    })
            },
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {

        val action by rememberImageAction(
            url = image.imageUrl
        )

        val painter = rememberImageActionPainter(action)

        val transition by animateFloatAsState(
            targetValue = if (action is ImageResult) 1f else 0f
        )

        Image(painter = painter, contentDescription = null, modifier = Modifier.graphicsLayer {
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

@Composable
fun PreviewDialog(
    isPreviewVisible: Boolean,
    imageUrl: String,
    dialogState: MaterialDialogState = rememberMaterialDialogState()
) {
    if (isPreviewVisible) {
        MaterialDialog(
            dialogState = dialogState,
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            properties = MaterialDialogProperties(
                resizable = false
            )
        ) { PreviewImage(imageUrl) }
        LaunchedEffect(imageUrl) {
            dialogState.show()
        }
    }
}

@Composable
fun PreviewImage(imageUrl: String) {
    val action by rememberImageAction(
        url = imageUrl
    )
    val painter = rememberImageActionPainter(action)

    val transition by animateFloatAsState(
        targetValue = if (action is ImageResult) 1f else 0f
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier.graphicsLayer {
            val animatedValue: Float = .8f + (.2f * transition)
            val blurValue: Float =
                if (transition == 1f) 1f else 24f + (.2f * transition)
            scaleX = animatedValue
            scaleY = animatedValue
            alpha = animatedValue
            renderEffect = BlurEffect(blurValue, blurValue)
        }.padding(24.dp).fillMaxSize()
    )
}

const val SNACK_MESSAGE = "1) Click image to open in browser\n" +
        "2) Long click to preview image"
