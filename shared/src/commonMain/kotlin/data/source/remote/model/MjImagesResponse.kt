package data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MjImagesResponse(
    @SerialName("currentPage")
    val currentPage: Int? = null,
    @SerialName("images")
    val mjImageResponses: List<MjImageResponse?>? = null,
    @SerialName("pageSize")
    val pageSize: Int? = null,
    @SerialName("totalImages")
    val totalImages: Int? = null,
    @SerialName("totalPages")
    val totalPages: Int? = null
)
