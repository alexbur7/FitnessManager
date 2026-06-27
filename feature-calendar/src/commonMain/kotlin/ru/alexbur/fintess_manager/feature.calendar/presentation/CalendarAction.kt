package ru.alexbur.fintess_manager.feature.calendar.presentation

internal sealed class CalendarAction {
    class DaySelected(val dayNumber: Int) : CalendarAction()
    class AddClicked : CalendarAction()
    class BackClicked : CalendarAction()
}
