package ru.alexbur.fintess_manager.presentation.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import kotlinx.serialization.Serializable
import ru.alexbur.fintess_manager.navigation.Navigator
import ru.alexbur.fintess_manager.navigation.Route
import ru.alexbur.fintess_manager.navigation.ScreenFactory
import ru.alexbur.fintess_manager.presentation.main.MainScreen

@Serializable
data object MainRoute : Route

internal class MainScreenFactory : ScreenFactory<MainRoute> {

    @NonRestartableComposable
    @Composable
    override fun create(navigator: Navigator, route: MainRoute) {
        MainScreen(navigator)
    }
}
