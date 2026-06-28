package ru.alexbur.fintess_manager.feature.calendar.presentation

internal sealed class CalendarAction {
    class DaySelected(val dayNumber: Int) : CalendarAction()
    data object NextWeek : CalendarAction()
    data object PreviousWeek : CalendarAction()
    data object Retry : CalendarAction()
}
