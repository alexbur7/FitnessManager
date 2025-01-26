package ru.alexbur.fintess_manager

import android.os.Build
import android.util.Log
import ru.alexbur.fintess_manager.base.NativeLogger

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

class AndroidLog : NativeLogger {
    override fun w(tag: String, data: String) {
        Log.d(tag, data)
    }

    override fun e(tag: String, data: String) {
        Log.e(tag, data)
    }

}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun createNativeLogger(): NativeLogger = AndroidLog()