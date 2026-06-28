package ru.alexbur.fintess_manager.presentation.factory

import androidx.navigation.NavGraphBuilder
import ru.alexbur.fintess_manager.feature.login.presentation.navigation.LoginRoute
import ru.alexbur.fintess_manager.feature.login.presentation.navigation.LoginScreenFactory
import ru.alexbur.fintess_manager.navigation.Navigator
import ru.alexbur.fintess_manager.presentation.main.navigation.MainRoute
import ru.alexbur.fintess_manager.presentation.main.navigation.MainScreenFactory

class AppComposeScreenFactory(
    private val builder: NavGraphBuilder,
) {

    fun create(navigator: Navigator) = with(builder) {
        create<LoginRoute>(navigator, LoginScreenFactory(MainRoute))
        create<MainRoute>(navigator, MainScreenFactory())
    }
}