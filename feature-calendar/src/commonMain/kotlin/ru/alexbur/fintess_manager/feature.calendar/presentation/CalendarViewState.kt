package ru.alexbur.fintess_manager.feature.calendar.presentation

import androidx.compose.runtime.Immutable
import ru.alexbur.fintess_manager.common_presentation.mvi.ViewState

@Immutable
internal data class CalendarViewState(
    val days: List<CalendarDay>,
    val selectedDayNumber: Int,
    val sectionTitle: String,
    val workouts: List<WorkoutItem>,
    val isLoading: Boolean = false,
) : ViewState

@Immutable
internal data class CalendarDay(
    val name: String,
    val number: Int,
)

@Immutable
internal data class WorkoutItem(
    val time: String,
    val duration: String,
    val title: String,
    val trainer: String,
    val status: WorkoutStatus,
)

internal enum class WorkoutStatus { Active, Soon }
