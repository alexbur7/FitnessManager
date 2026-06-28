package ru.alexbur.fintess_manager.feature.calendar.data.mapper

import ru.alexbur.fintess_manager.feature.calendar.data.models.response.AppointmentResponse
import ru.alexbur.fintess_manager.feature.calendar.domain.models.Appointment

internal class CalendarMapper {
    fun map(response: AppointmentResponse): Appointment = Appointment(
        id = response.id,
        dayNumber = response.dayNumber,
        time = response.time,
        duration = response.duration,
        title = response.title,
        clientName = response.clientName,
        status = Appointment.Status.create(response.status),
    )
}
