package ru.alexbur.fintess_manager.feature.clients.domain.models

internal data class Client(
    val relationshipsId: String,
    val name: String,
    val remainingWorkouts: Int,
    val workoutsStatus: String?,
)
