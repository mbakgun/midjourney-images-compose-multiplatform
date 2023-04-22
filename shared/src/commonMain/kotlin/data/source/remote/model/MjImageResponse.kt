package data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MjImageResponse(
    @SerialName("date")
    val date: String?,
    @SerialName("imageUrl")
    val imageUrl: String?,
    @SerialName("ratio")
    val ratio: Double?
)
