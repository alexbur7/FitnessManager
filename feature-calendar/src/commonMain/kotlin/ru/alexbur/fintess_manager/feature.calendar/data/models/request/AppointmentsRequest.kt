package ru.alexbur.fintess_manager.feature.calendar.data.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AppointmentsRequest(
    @SerialName("start_date") val startDate: String,
    @SerialName("end_date") val endDate: String,
)
