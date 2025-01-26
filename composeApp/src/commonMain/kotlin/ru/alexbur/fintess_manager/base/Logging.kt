package ru.alexbur.fintess_manager.base

import ru.alexbur.fintess_manager.createNativeLogger

interface NativeLogger {
    fun w(tag: String, data: String)
    fun e(tag: String, data: String)
}

object AppLogger {
    private val logger = createNativeLogger()

    fun w(tag: String, data: String) = logger.w(tag, data)
    fun e(tag: String, data: String) = logger.e(tag, data)
}