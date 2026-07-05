package ru.alexbur.fintess_manager.feature.clients.data.models.response

import kotlinx.serialization.Serializable

@Serializable
internal data class ClientTrainingsResponse(
    val trainings: List<ClientTrainingResponse>,
)
