@file:OptIn(ExperimentalFoundationApi::class)

package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.model.ImageResult
import com.seiko.imageloader.rememberImageAction
import com.seiko.imageloader.rememberImageActionPainter
import domain.model.MjImage
import domain.model.MjImages
import domain.model.State
import kotlin.math.roundToInt
import kotlinx.coroutines.launch
import ui.theme.AppTheme
import util.OnBottomReached
import util.generateImageLoader

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MjImagesApp(
    viewModel: MjImagesViewModel
) {
    CompositionLocalProvider(
        LocalImageLoader provides remember { generateImageLoader() },
    ) {
        val useDarkTheme by viewModel.useDarkTheme.collectAsState(false)
        AppTheme(useDarkTheme = useDarkTheme) {

            val images: MjImages by viewModel.images.collectAsState()
            val state: State by viewModel.state.collectAsState()
            val previewUrl by viewModel.dialogPreviewUrl.collectAsState()
            val onRefresh = viewModel::refreshImages

            val scaffoldState: ScaffoldState = rememberScaffoldState()
            val listState = rememberLazyStaggeredGridState()
            val scope = rememberCoroutineScope()

            val showButton by remember {
                derivedStateOf {
                    listState.firstVisibleItemIndex > 0
                }
            }

            LaunchedEffect(Unit) {
                if (viewModel.isEligibleToShowSnackBar()) {
                    scaffoldState.snackbarHostState.showSnackbar(SNACK_MESSAGE)
                    viewModel.setSnackMessageShown()
                }
            }

            Scaffold(scaffoldState = scaffoldState) {
                val isRefreshing = remember {
                    derivedStateOf {
                        state == State.LOADING
                    }
                }
                val pullRefreshState = rememberPullRefreshState(isRefreshing.value, onRefresh)

                Box(
                    Modifier
                        .fillMaxSize()
                        .pullRefresh(state = pullRefreshState)
                ) {
                    when (state) {
                        State.ERROR -> ErrorScreen(onRefresh)
                        State.EMPTY -> EmptyScreen(onRefresh)
                        else -> MjImagesList(
                            onLoadMore = viewModel::loadMore,
                            images = images,
                            state = listState,
                        ) { imageUrl ->
                            viewModel.showPreviewDialog(imageUrl)
                        }
                    }
                    PullRefreshIndicator(
                        refreshing = isRefreshing.value,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter)
                            .testTag("pullRefreshIndicator")
                    )

                    AnimatedVisibility(
                        visible = showButton,
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        ScrollToTopButton(onClick = {
                            scope.launch {
                                listState.animateScrollToItem(0)
                            }
                        })
                    }

                    DraggableThemeSelection(
                        useDarkTheme,
                        viewModel::setDarkMode
                    )

                    if (previewUrl.isNotEmpty()) {
                        PreviewDialog(
                            imageUrl = previewUrl,
                            onDismissed = viewModel::dismissPreviewDialog
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MjImagesList(
    images: MjImages,
    state: LazyStaggeredGridState,
    onLoadMore: () -> Unit,
    showPreviewDialog: (imageUrl: String) -> Unit,
) {
    PlatformSpecificMjImagesGrid(
        onLoadMore = onLoadMore,
        images = images,
        modifier = Modifier.fillMaxSize().testTag("imagesGrid"),
        showPreviewDialog = showPreviewDialog,
        state = state,
    )
}

@Composable
fun PlatformSpecificMjImagesGrid(
    state: LazyStaggeredGridState,
    images: MjImages,
    showPreviewDialog: (imageUrl: String) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalStaggeredGrid(
        state = state.apply { OnBottomReached(onLoadMore::invoke) },
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier,
    ) {
        items(
            items = images.images,
            key = MjImage::imageUrl
        ) { image ->
            MjImageItem(
                image = image,
                showPreviewDialog = showPreviewDialog,
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MjImageItem(
    image: MjImage,
    showPreviewDialog: (imageUrl: String) -> Unit,
) {
    val uriHandler = LocalUriHandler.current
    val height = remember { derivedStateOf((180 * image.ratio)::dp) }

    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(height.value)
            .combinedClickable(
                onClick = { uriHandler.openUri(image.imageUrl) },
                onLongClick = { showPreviewDialog.invoke(image.imageUrl) },
            ),
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {

        val action by rememberImageAction(
            url = image.imageUrl
        )

        val painter = rememberImageActionPainter(action, filterQuality = FilterQuality.None)

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
        }, contentScale = ContentScale.Crop)
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
    imageUrl: String,
    onDismissed: () -> Unit,
) {
    Popup(
        onDismissRequest = {
            onDismissed()
        },
        properties = PopupProperties(
            focusable = true
        ),
        content = {
            Box(contentAlignment = Alignment.TopEnd) {
                PreviewImage(imageUrl)
                Button(
                    onClick = { onDismissed() },
                    modifier = Modifier
                        .padding(24.dp)
                        .shadow(10.dp, shape = CircleShape)
                        .clip(shape = CircleShape)
                        .size(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.onSurface,
                    )
                ) {
                    Icon(Icons.Filled.Close, "close")
                }
            }
        }
    )
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
        contentScale = ContentScale.Crop,
        modifier = Modifier.graphicsLayer {
            val animatedValue: Float = .8f + (.2f * transition)
            val blurValue: Float =
                if (transition == 1f) 1f else 24f + (.2f * transition)
            scaleX = animatedValue
            scaleY = animatedValue
            alpha = animatedValue
            renderEffect = BlurEffect(blurValue, blurValue)
        }.fillMaxSize()
    )
}

@Composable
fun DraggableThemeSelection(
    useDarkTheme: Boolean,
    onClick: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        var offsetY by remember { mutableStateOf(0f) }

        Surface(
            modifier = Modifier
                .offset { IntOffset(0, offsetY.roundToInt()) }
                .align(Alignment.CenterEnd)
                .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
        ) {
            Icon(
                imageVector = if (useDarkTheme) Icons.Rounded.LightMode else Icons.Rounded.DarkMode,
                contentDescription = "Change theme color",
                modifier = Modifier
                    .size(36.dp)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            offsetY += dragAmount.y
                        }
                    }
                    .rotate(230f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onClick(useDarkTheme.not()) }
            )
        }
    }
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxSize()
            .padding(bottom = 24.dp, end = 25.dp), Alignment.BottomEnd
    ) {
        Button(
            onClick = { onClick() },
            modifier = Modifier
                .shadow(10.dp, shape = CircleShape)
                .clip(shape = CircleShape)
                .size(50.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.onSurface,
            )
        ) {
            Icon(Icons.Filled.KeyboardArrowUp, "arrow up")
        }
    }
}

const val SNACK_MESSAGE = "1) Click image to open in browser\n" +
        "2) Long click to preview image"
