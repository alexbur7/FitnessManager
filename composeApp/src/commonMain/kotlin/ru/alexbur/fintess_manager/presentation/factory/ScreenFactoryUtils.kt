package ru.alexbur.fintess_manager.presentation.factory

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ru.alexbur.fintess_manager.navigation.Navigator
import ru.alexbur.fintess_manager.navigation.Route
import ru.alexbur.fintess_manager.navigation.ScreenFactory

internal inline fun <reified T : Route> NavGraphBuilder.create(
    navigator: Navigator,
    factory: ScreenFactory<T>
) {
    this.composable<T> { stackEntry ->
        val data = stackEntry.toRoute<T>()
        factory.create(navigator, data)
    }
}