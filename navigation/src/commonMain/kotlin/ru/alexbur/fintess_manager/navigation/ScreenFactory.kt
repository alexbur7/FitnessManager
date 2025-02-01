package ru.alexbur.fintess_manager.navigation

import androidx.compose.runtime.Composable

interface ScreenFactory<in T : Route> {

    @Composable
    fun create(navigator: Navigator, route: T)
}