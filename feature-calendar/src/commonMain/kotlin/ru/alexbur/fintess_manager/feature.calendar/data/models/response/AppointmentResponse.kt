package ru.alexbur.fintess_manager.feature.calendar.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AppointmentResponse(
    @SerialName("id") val id: Long,
    @SerialName("day_number") val dayNumber: Int,
    @SerialName("time") val time: String,
    @SerialName("duration") val duration: String,
    @SerialName("title") val title: String,
    @SerialName("client_name") val clientName: String,
    @SerialName("status") val status: String,
)
