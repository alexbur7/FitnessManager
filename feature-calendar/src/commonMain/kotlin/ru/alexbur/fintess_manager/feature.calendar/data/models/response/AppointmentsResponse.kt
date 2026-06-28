package ru.alexbur.fintess_manager.feature.calendar.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AppointmentsResponse(
    @SerialName("appointments")
    val appointments: List<AppointmentResponse>,
)
