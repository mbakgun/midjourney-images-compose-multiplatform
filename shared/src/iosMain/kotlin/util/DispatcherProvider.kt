package util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal actual fun getDispatcherProvider(): DispatcherProvider = IosDispatcherProvider()

private class IosDispatcherProvider : DispatcherProvider {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
    override val unconfined = Dispatchers.Unconfined
}
