package ru.alexbur.fintess_manager.feature.calendar.domain.repository

import ru.alexbur.fintess_manager.feature.calendar.domain.models.Appointment

internal interface CalendarRepository {
    suspend fun getAppointments(): List<Appointment>
}