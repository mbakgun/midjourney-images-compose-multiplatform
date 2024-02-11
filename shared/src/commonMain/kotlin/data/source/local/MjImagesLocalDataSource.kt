package data.source.local

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import data.source.MjImagesDataSource
import kotlinx.coroutines.withContext
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

    companion object {
        private const val SNACK_MESSAGE_KEY = "SNACK_MESSAGE_KEY"
        private const val DARK_MODE_KEY = "DARK_MODE_KEY"
    }
}
