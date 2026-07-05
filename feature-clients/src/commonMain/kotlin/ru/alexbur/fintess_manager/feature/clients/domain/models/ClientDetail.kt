package ru.alexbur.fintess_manager.feature.clients.domain.models

import kotlinx.datetime.LocalDate

internal data class ClientDetail(
    val relationshipsId: String,
    val name: String,
    val remainingWorkouts: Int,
    val workoutsStatus: String?,
    val weightKg: Double,
    val heightCm: Int,
    val age: Int,
    val clientSince: LocalDate,
)
