package ru.alexbur.fintess_manager.core

actual fun createNativeLogger(): NativeLogger = IosLogger()

private class IosLogger : NativeLogger {
    override fun w(tag: String, data: String) {
        print(data)
    }

    override fun e(tag: String, data: String) {
        print(data)
    }
}