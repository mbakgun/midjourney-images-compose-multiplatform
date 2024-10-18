package domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class MjImage @OptIn(ExperimentalUuidApi::class) constructor(
    val id: String = Uuid.random().toString(),
    val date: String,
    val imageUrl: String,
    val hqImageUrl: String,
    val ratio: Double
)
