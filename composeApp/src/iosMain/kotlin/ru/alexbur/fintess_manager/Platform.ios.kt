package ru.alexbur.fintess_manager

import platform.UIKit.UIDevice
import ru.alexbur.fintess_manager.base.NativeLogger

class IOSPlatform : Platform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

class IosLogger : NativeLogger {
    override fun w(tag: String, data: String) {
        print(data)
    }

    override fun e(tag: String, data: String) {
        print(data)
    }
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun createNativeLogger(): NativeLogger = IosLogger()