package ru.alexbur.fintess_manager.feature.calendar.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class CalendarViewModel : ViewModel() {

    private val _viewState = MutableStateFlow(
        CalendarViewState(
            days = listOf(
                CalendarDay(name = "Пн", number = 23),
                CalendarDay(name = "Вт", number = 24),
                CalendarDay(name = "Ср", number = 25),
                CalendarDay(name = "Чт", number = 26),
                CalendarDay(name = "Пт", number = 27),
                CalendarDay(name = "Сб", number = 28),
                CalendarDay(name = "Вс", number = 29),
            ),
            selectedDayNumber = 26,
            sectionTitle = "Сегодня — 2 тренировки",
            workouts = listOf(
                WorkoutItem(
                    time = "08:00",
                    duration = "60 мин",
                    title = "Силовая",
                    trainer = "Алексей Смирнов",
                    status = WorkoutStatus.Active,
                ),
                WorkoutItem(
                    time = "11:30",
                    duration = "45 мин",
                    title = "Кардио",
                    trainer = "Мария Козлова",
                    status = WorkoutStatus.Soon,
                ),
            ),
        ),
    )
    val viewState: StateFlow<CalendarViewState> = _viewState.asStateFlow()

    fun obtainAction(action: CalendarAction) {
        when (action) {
            is CalendarAction.DaySelected -> selectDay(action.dayNumber)
        }
    }

    private fun selectDay(dayNumber: Int) {
        _viewState.value = _viewState.value.copy(selectedDayNumber = dayNumber)
    }
}
