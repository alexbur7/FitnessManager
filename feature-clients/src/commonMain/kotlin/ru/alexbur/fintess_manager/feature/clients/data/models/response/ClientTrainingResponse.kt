package ru.alexbur.fintess_manager.feature.clients.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ClientTrainingResponse(
    val id: Long,
    @SerialName("start_time")
    val startTime: String,
    @SerialName("end_time")
    val endTime: String,
    @SerialName("duration_minutes")
    val durationMinutes: Int,
    val title: String,
    val status: String,
)
