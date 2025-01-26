package ru.alexbur.fintess_manager.presentation

import ru.alexbur.fintess_manager.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}