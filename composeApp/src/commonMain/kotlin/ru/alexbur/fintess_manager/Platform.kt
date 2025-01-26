package ru.alexbur.fintess_manager

import ru.alexbur.fintess_manager.base.NativeLogger

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun createNativeLogger(): NativeLogger