package ru.alexbur.fintess_manager.feature.clients.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ClientDetailResponse(
    @SerialName("relationships_id")
    val relationshipsId: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("remaining_workouts")
    val remainingWorkouts: Int,
    @SerialName("workouts_status")
    val workoutsStatus: String? = null,
    @SerialName("weight_kg")
    val weightKg: Double,
    @SerialName("height_cm")
    val heightCm: Int,
    val age: Int,
    @SerialName("client_since")
    val clientSince: String,
)
