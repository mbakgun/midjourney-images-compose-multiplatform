package domain.mapper

import data.source.remote.model.MjImageResponse
import data.source.remote.model.MjImagesResponse
import domain.model.MjImage
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import util.getDispatcherProvider

class MjImagesMapperTest {

    private lateinit var mapper: MjImagesMapper

    @BeforeTest
    fun setup() {
        mapper = MjImagesMapper(getDispatcherProvider())
    }

    @Test
    fun givenNullCurrentPageWhenMapMjImagesCalledThenCurrentPageReturnsZero() = runTest {
        // given
        val response = mockMjImagesResponse(currentPage = null)

        // when
        val result = mapper.mapMjImages(response)

        // then
        assertEquals(0, result.currentPage)
    }

    @Test
    fun givenNullTotalPagesWhenMapMjImagesCalledThenTotalPagesReturnsZero() = runTest {
        // given
        val response = mockMjImagesResponse(totalPages = null)

        // when
        val result = mapper.mapMjImages(response)

        // then
        assertEquals(0, result.totalPages)
    }

    @Test
    fun givenNullMjImageResponsesWhenMapMjImagesCalledThenImagesReturnsEmpty() =
        runTest {
            // given
            val response = mockMjImagesResponse(mjImageResponses = null)

            // when
            val result = mapper.mapMjImages(response)

            // then
            assertEquals(emptyList(), result.images)
        }

    @Test
    fun givenNullDateWhenMapMjImagesCalledThenImagesReturnsEmpty() = runTest {
        // given
        val response = mockMjImagesResponse(
            mjImageResponses = listOf(
                mockMjImageResponse(date = null)
            )
        )
        // when
        val result = mapper.mapMjImages(response)

        // then
        assertEquals(emptyList(), result.images)
    }

    @Test
    fun givenNullImageUrlWhenMapMjImagesCalledThenImagesReturnsEmpty() = runTest {
        // given
        val response = mockMjImagesResponse(
            mjImageResponses = listOf(
                mockMjImageResponse(imageUrl = null)
            )
        )
        // when
        val result = mapper.mapMjImages(response)

        // then
        assertEquals(emptyList(), result.images)
    }

    @Test
    fun givenNullRatioWhenMapMjImagesCalledThenImagesReturnsEmpty() = runTest {
        // given
        val response = mockMjImagesResponse(
            mjImageResponses = listOf(
                mockMjImageResponse(ratio = null)
            )
        )
        // when
        val result = mapper.mapMjImages(response)

        // then
        assertEquals(emptyList(), result.images)
    }

    @Test
    fun givenValidResponseWhenMapMjImagesCalledThenImagesReturnsValid() = runTest {
        // given
        val response = mockMjImagesResponse(
            mjImageResponses = listOf(
                mockMjImageResponse(
                    date = "2023-21-04",
                    imageUrl = "https://mj.akgns.com",
                    ratio = 1.0
                )
            )
        )
        // when
        val result = mapper.mapMjImages(response)

        // then
        assertEquals(
            listOf(
                MjImage(
                    date = "2023-21-04",
                    imageUrl = "https://mj.akgns.com",
                    ratio = 1.0
                )
            ),
            result.images
        )
    }

    @Test
    fun givenValidResponseWhenMapMjImagesCalledThenImagesReturnsList() = runTest {
        // given
        val response = mockMjImagesResponse(
            mjImageResponses = listOf(
                mockMjImageResponse(
                    date = "2023-21-04",
                    imageUrl = "https://mj.akgns.com",
                    ratio = 1.0
                ),
                mockMjImageResponse(
                    date = "2023-21-04",
                    imageUrl = "https://mj.akgns.com/images",
                    ratio = 1.0
                )
            )
        )
        // when
        val result = mapper.mapMjImages(response)

        // then
        assertEquals(
            listOf(
                MjImage(
                    date = "2023-21-04",
                    imageUrl = "https://mj.akgns.com",
                    ratio = 1.0
                ),
                MjImage(
                    date = "2023-21-04",
                    imageUrl = "https://mj.akgns.com/images",
                    ratio = 1.0
                )
            ),
            result.images
        )
    }

    private fun mockMjImagesResponse(
        currentPage: Int? = null,
        totalPages: Int? = null,
        mjImageResponses: List<MjImageResponse?>? = null
    ): MjImagesResponse =
        MjImagesResponse(
            currentPage = currentPage,
            totalPages = totalPages,
            mjImageResponses = mjImageResponses,
            pageSize = null,
            totalImages = null,
        )

    private fun mockMjImageResponse(
        date: String? = null,
        imageUrl: String? = null,
        ratio: Double? = null
    ): MjImageResponse =
        MjImageResponse(
            date = date,
            imageUrl = imageUrl,
            ratio = ratio,
        )
}
