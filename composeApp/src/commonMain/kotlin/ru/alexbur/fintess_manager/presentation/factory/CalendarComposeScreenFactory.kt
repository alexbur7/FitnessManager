package ru.alexbur.fintess_manager.presentation.factory

import androidx.navigation.NavGraphBuilder
import ru.alexbur.fintess_manager.feature.calendar.presentation.navigation.CalendarRoute
import ru.alexbur.fintess_manager.feature.calendar.presentation.navigation.CalendarScreenFactory
import ru.alexbur.fintess_manager.navigation.Navigator

class CalendarComposeScreenFactory(
    private val builder: NavGraphBuilder,
) {

    fun create(navigator: Navigator) = with(builder) {
        create<CalendarRoute>(navigator, CalendarScreenFactory())
    }
}