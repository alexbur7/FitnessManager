package ru.alexbur.fintess_manager.feature.calendar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import ru.alexbur.fintess_manager.common_presentation.EventFlow
import ru.alexbur.fintess_manager.common_presentation.MutableEventFlow
import ru.alexbur.fintess_manager.common_presentation.error_handler.FitnessManagerErrorHandler
import ru.alexbur.fintess_manager.feature.calendar.domain.interactor.CalendarInteractor
import ru.alexbur.fintess_manager.feature.calendar.domain.models.Appointment

internal class CalendarViewModel(
    private val interactor: CalendarInteractor,
    private val errorHandler: FitnessManagerErrorHandler,
) : ViewModel() {

    private val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    private var monday = today.minus(today.dayOfWeek.ordinal, DateTimeUnit.DAY)

    private val dayNames = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")

    private fun buildWeekDays(weekMonday: LocalDate): List<CalendarDay> = (0..6).map { i ->
        val date = weekMonday.plus(i, DateTimeUnit.DAY)
        CalendarDay(
            name = dayNames[i],
            number = date.dayOfMonth,
            isToday = date == today,
        )
    }

    private val _viewState = MutableStateFlow(
        CalendarViewState(
            days = buildWeekDays(monday),
            selectedDayNumber = today.dayOfMonth,
            sectionTitle = "",
            workouts = emptyList(),
            isLoading = true,
        ),
    )
    val viewState: StateFlow<CalendarViewState> = _viewState.asStateFlow()

    private val _viewEvent = MutableEventFlow()
    val viewEvent: EventFlow = _viewEvent

    private var allAppointments: List<Appointment>? = null

    init {
        loadAppointments()
    }

    fun obtainAction(action: CalendarAction) {
        when (action) {
            is CalendarAction.DaySelected -> selectDay(action.dayNumber)
            CalendarAction.NextWeek -> shiftWeek(offset = 1)
            CalendarAction.PreviousWeek -> shiftWeek(offset = -1)
        }
    }

    private fun shiftWeek(offset: Int) {
        monday = monday.plus(offset * 7, DateTimeUnit.DAY)
        val newDays = buildWeekDays(monday)
        val newSelectedDay = newDays.firstOrNull { it.isToday }?.number ?: newDays.first().number
        allAppointments = null
        _viewState.value = _viewState.value.copy(
            days = newDays,
            selectedDayNumber = newSelectedDay,
            workouts = emptyList(),
            sectionTitle = "",
            isLoading = true,
        )
        loadAppointments()
    }

    private fun loadAppointments() {
        val start = monday.toString()
        val end = monday.plus(6, DateTimeUnit.DAY).toString()
        viewModelScope.launch {
            interactor.getAppointments(startDate = start, endDate = end)
                .onSuccess { appointments ->
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
        val day = _viewState.value.days.find { it.number == dayNumber }
        _viewState.value = _viewState.value.copy(
            selectedDayNumber = dayNumber,
            workouts = filtered.map { it.toWorkoutItem() },
            sectionTitle = buildSectionTitle(
                count = filtered.size,
                isToday = day?.isToday ?: false,
                dayName = day?.name.orEmpty(),
            ),
            isLoading = false,
        )
    }

    private fun buildSectionTitle(count: Int, isToday: Boolean, dayName: String): String {
        val prefix = if (isToday) "Сегодня" else dayName
        val word = when {
            count % 100 in 11..14 -> "тренировок"
            count % 10 == 1 -> "тренировка"
            count % 10 in 2..4 -> "тренировки"
            else -> "тренировок"
        }
        return "$prefix — $count $word"
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
