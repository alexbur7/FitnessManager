package ru.alexbur.fintess_manager.feature.login.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import kotlinx.serialization.Serializable
import ru.alexbur.fintess_manager.feature.login.presentation.LoginScreen
import ru.alexbur.fintess_manager.navigation.Navigator
import ru.alexbur.fintess_manager.navigation.Route
import ru.alexbur.fintess_manager.navigation.ScreenFactory

@Serializable
data object LoginRoute : Route

class LoginScreenFactory : ScreenFactory<LoginRoute> {

    @NonRestartableComposable
    @Composable
    override fun create(navigator: Navigator, route: LoginRoute) {
        LoginScreen(navigator)
    }
}