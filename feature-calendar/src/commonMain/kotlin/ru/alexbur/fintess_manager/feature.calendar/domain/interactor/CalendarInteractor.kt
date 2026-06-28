package ru.alexbur.fintess_manager.feature.calendar.domain.interactor

import ru.alexbur.fintess_manager.core.utils.safeRunCatching
import ru.alexbur.fintess_manager.feature.calendar.domain.repository.CalendarRepository

internal class CalendarInteractor(
    private val repository: CalendarRepository,
) {
    suspend fun getAppointments() = safeRunCatching {
        repository.getAppointments()
    }
}
