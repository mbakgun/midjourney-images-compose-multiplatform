package util

import kotlinx.coroutines.Dispatchers

internal actual fun getDispatcherProvider(): DispatcherProvider = WasmDispatcherProvider()

private class WasmDispatcherProvider : DispatcherProvider {
    override val main = Dispatchers.Main
    override val io = Dispatchers.Default
    override val default = Dispatchers.Default
    override val unconfined = Dispatchers.Unconfined
}
