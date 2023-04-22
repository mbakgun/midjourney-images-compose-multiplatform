package data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MjImagesResponse(
    @SerialName("currentPage")
    val currentPage: Int?,
    @SerialName("images")
    val mjImageResponses: List<MjImageResponse?>?,
    @SerialName("pageSize")
    val pageSize: Int?,
    @SerialName("totalImages")
    val totalImages: Int?,
    @SerialName("totalPages")
    val totalPages: Int?
)
