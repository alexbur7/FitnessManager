package ru.alexbur.fintess_manager.feature.clients.domain.repository

import ru.alexbur.fintess_manager.feature.clients.domain.models.ClientDetail
import ru.alexbur.fintess_manager.feature.clients.domain.models.ClientTraining

internal interface ClientTrainingsRepository {
    suspend fun getClientDetail(clientId: String): ClientDetail
    suspend fun getClientTrainings(clientId: String, date: String): List<ClientTraining>
}
