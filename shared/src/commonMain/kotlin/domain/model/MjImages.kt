package domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class MjImages(
    val currentPage: Int = 1,
    val totalPages: Int = 1,
    val images: List<MjImage> = emptyList(),
) {

    fun isEmpty() = images.isEmpty()

    operator fun plus(other: MjImages): MjImages =
        MjImages(
            currentPage = other.currentPage,
            images = (this.images + other.images).distinctBy(MjImage::imageUrl),
            totalPages = other.totalPages
        )
}
