package ru.alexbur.fintess_manager.navigation

import androidx.compose.runtime.Composable

interface ScreenFactory<in T : Route> {

    val type: Type get() = Type.SCREEN

    @Composable
    fun create(navigator: Navigator, route: T)

    enum class Type {
        SCREEN,
        DIALOG;
    }
}