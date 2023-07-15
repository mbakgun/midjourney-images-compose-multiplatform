package fakes

import data.source.MjImagesDataSource

class MjImagesLocalFakeDataSource : MjImagesDataSource.Local {

    override suspend fun isEligibleToShowSnackMessage(): Boolean =
        true

    override suspend fun setSnackMessageShown() = Unit

    override suspend fun isDarkModeEnabled(): Boolean = false

    override suspend fun setDarkMode(enabled: Boolean) = Unit
}
