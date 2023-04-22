package domain.model

data class MjImages(
    val currentPage: Int = 1,
    val totalPages: Int = 1,
    val images: List<MjImage> = emptyList(),
) {

    fun isEmpty() = images.isEmpty()

    operator fun plus(images: MjImages): MjImages =
        MjImages(
            currentPage = images.currentPage,
            images = this.images + images.images,
            totalPages = images.totalPages
        )
}
