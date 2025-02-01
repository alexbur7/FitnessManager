package ru.alexbur.fintess_manager.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ru.alexbur.fintess_manager.feature.calendar.presentation.navigation.CalendarRoute
import ru.alexbur.fintess_manager.feature.calendar.presentation.navigation.CalendarScreenFactory
import ru.alexbur.fintess_manager.feature.login.presentation.navigation.LoginRoute
import ru.alexbur.fintess_manager.feature.login.presentation.navigation.LoginScreenFactory
import ru.alexbur.fintess_manager.navigation.Navigator
import ru.alexbur.fintess_manager.navigation.Route
import ru.alexbur.fintess_manager.navigation.ScreenFactory

class AppComposeScreenFactory(
    private val builder: NavGraphBuilder,
) {

    fun create(navigator: Navigator) {
        create<LoginRoute>(navigator, LoginScreenFactory(CalendarRoute))
        create<CalendarRoute>(navigator, CalendarScreenFactory())
    }

    private inline fun <reified T : Route> create(navigator: Navigator, factory: ScreenFactory<T>) {
        builder.composable<T> { stackEntry ->
            val data = stackEntry.toRoute<T>()
            factory.create(navigator, data)
        }
    }
}