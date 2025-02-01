package ru.alexbur.fintess_manager.feature.calendar.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import kotlinx.serialization.Serializable
import ru.alexbur.fintess_manager.feature.calendar.presentation.CalendarScreen
import ru.alexbur.fintess_manager.navigation.Navigator
import ru.alexbur.fintess_manager.navigation.Route
import ru.alexbur.fintess_manager.navigation.ScreenFactory

@Serializable
data object CalendarRoute : Route

class CalendarScreenFactory : ScreenFactory<CalendarRoute> {

    @NonRestartableComposable
    @Composable
    override fun create(
        navigator: Navigator,
        route: CalendarRoute
    ) {
        CalendarScreen(navigator)
    }
}