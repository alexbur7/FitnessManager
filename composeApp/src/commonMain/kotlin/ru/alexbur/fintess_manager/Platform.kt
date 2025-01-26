package ru.alexbur.fintess_manager

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform