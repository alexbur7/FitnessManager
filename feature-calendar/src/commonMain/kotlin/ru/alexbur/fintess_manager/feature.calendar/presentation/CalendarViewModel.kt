package ru.alexbur.fintess_manager.feature.calendar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.alexbur.fintess_manager.common_presentation.EventFlow
import ru.alexbur.fintess_manager.common_presentation.MutableEventFlow
import ru.alexbur.fintess_manager.common_presentation.error_handler.FitnessManagerErrorHandler
import ru.alexbur.fintess_manager.feature.calendar.domain.interactor.CalendarInteractor
import ru.alexbur.fintess_manager.feature.calendar.domain.models.Appointment

internal class CalendarViewModel(
    private val interactor: CalendarInteractor,
    private val errorHandler: FitnessManagerErrorHandler,
) : ViewModel() {

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
            sectionTitle = "",
            workouts = emptyList(),
            isLoading = true,
        ),
    )
    val viewState: StateFlow<CalendarViewState> = _viewState.asStateFlow()

    private val _viewEvent = MutableEventFlow()
    val viewEvent: EventFlow = _viewEvent

    // null means data not yet loaded; empty list means loaded but no appointments
    private var allAppointments: List<Appointment>? = null

    init {
        loadAppointments()
    }

    fun obtainAction(action: CalendarAction) {
        when (action) {
            is CalendarAction.DaySelected -> selectDay(action.dayNumber)
        }
    }

    private fun loadAppointments() {
        viewModelScope.launch {
            interactor.getAppointments().onSuccess { appointments ->
                allAppointments = appointments
                updateStateForDay(_viewState.value.selectedDayNumber)
            }.onFailure { error ->
                _viewState.value = _viewState.value.copy(isLoading = false)
                _viewEvent.send(errorHandler.handleError(error))
            }
        }
    }

    private fun selectDay(dayNumber: Int) {
        if (allAppointments != null) {
            updateStateForDay(dayNumber)
        } else {
            _viewState.value = _viewState.value.copy(selectedDayNumber = dayNumber)
        }
    }

    private fun updateStateForDay(dayNumber: Int) {
        val filtered = allAppointments.orEmpty().filter { it.dayNumber == dayNumber }
        _viewState.value = _viewState.value.copy(
            selectedDayNumber = dayNumber,
            workouts = filtered.map { it.toWorkoutItem() },
            sectionTitle = buildSectionTitle(filtered.size),
            isLoading = false,
        )
    }

    private fun buildSectionTitle(count: Int): String {
        val word = when {
            count % 100 in 11..14 -> "тренировок"
            count % 10 == 1 -> "тренировка"
            count % 10 in 2..4 -> "тренировки"
            else -> "тренировок"
        }
        return "Сегодня — $count $word"
    }

    private fun Appointment.toWorkoutItem() = WorkoutItem(
        time = time,
        duration = duration,
        title = title,
        trainer = clientName,
        status = when (status) {
            Appointment.Status.ACTIVE -> WorkoutStatus.Active
            Appointment.Status.SOON -> WorkoutStatus.Soon
        },
    )
}
