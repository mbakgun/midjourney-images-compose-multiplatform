package data.source.local

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import data.source.MjImagesDataSource
import data.source.remote.model.MjImagesResponse
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import util.DispatcherProvider

class MjImagesLocalDataSource(
    private val settings: Settings,
    private val dispatcherProvider: DispatcherProvider,
) : MjImagesDataSource.Local {
    override suspend fun isEligibleToShowSnackMessage(): Boolean =
        withContext(dispatcherProvider.io) {
            settings.getBoolean(SNACK_MESSAGE_KEY, true)
        }

    override suspend fun setSnackMessageShown() {
        withContext(dispatcherProvider.io) {
            settings[SNACK_MESSAGE_KEY] = false
        }
    }

    override suspend fun isDarkModeEnabled(): Boolean =
        withContext(dispatcherProvider.io) {
            settings.getBoolean(DARK_MODE_KEY, false)
        }

    override suspend fun setDarkMode(enabled: Boolean) {
        withContext(dispatcherProvider.io) {
            settings[DARK_MODE_KEY] = enabled
        }
    }

    override suspend fun isCacheValid(): Boolean =
        withContext(dispatcherProvider.io) {
            settings.hasKey(CACHE_PREFIX_KEY + 1)
        }

    override suspend fun getImages(page: Int): MjImagesResponse? =
        withContext(dispatcherProvider.io) {
            val jsonString = settings.getStringOrNull(CACHE_PREFIX_KEY + page) ?: return@withContext null
            Json.decodeFromString<MjImagesResponse?>(jsonString)
        }

    override suspend fun clearImages() {
        withContext(dispatcherProvider.io) {
            settings.keys.filter {
                it.startsWith(CACHE_PREFIX_KEY)
            }.forEach {
                settings.remove(it)
            }
        }
    }

    override suspend fun saveImages(page: Int, response: MjImagesResponse) {
        withContext(dispatcherProvider.io) {
            if (response.mjImageResponses.isNullOrEmpty()) return@withContext
            if (page == 1) clearImages()
            settings[CACHE_PREFIX_KEY + page] = Json.encodeToString(response)
        }
    }

    companion object {
        private const val SNACK_MESSAGE_KEY = "SNACK_MESSAGE_KEY"
        private const val DARK_MODE_KEY = "DARK_MODE_KEY"
        private const val CACHE_PREFIX_KEY = "CACHE_PAGE_"
    }
}
