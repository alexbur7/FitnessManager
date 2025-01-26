package ru.alexbur.fintess_manager.core

import android.util.Log

class AndroidLog : NativeLogger {
    override fun w(tag: String, data: String) {
        Log.d(tag, data)
    }

    override fun e(tag: String, data: String) {
        Log.e(tag, data)
    }

}

actual fun createNativeLogger(): NativeLogger = AndroidLog()