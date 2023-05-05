package util

import kotlinx.coroutines.Dispatchers

internal actual fun getDispatcherProvider(): DispatcherProvider = AndroidDispatcherProvider()

private class AndroidDispatcherProvider : DispatcherProvider {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
    override val unconfined = Dispatchers.Unconfined
}
