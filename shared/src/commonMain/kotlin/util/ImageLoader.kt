package util

import androidx.compose.runtime.Composable
import com.seiko.imageloader.ImageLoader

@Composable
expect fun generateImageLoader(): ImageLoader
