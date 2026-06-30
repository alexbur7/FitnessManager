package ru.alexbur.fintess_manager.feature.clients.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ClientResponse(
    @SerialName("id")
    val id: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("remaining_workouts")
    val remainingWorkouts: Int,
    @SerialName("workouts_status")
    val workoutsStatus: String? = null,
)
