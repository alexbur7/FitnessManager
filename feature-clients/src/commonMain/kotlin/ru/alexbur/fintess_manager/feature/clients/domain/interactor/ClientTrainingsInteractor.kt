package ru.alexbur.fintess_manager.feature.clients.domain.interactor

import ru.alexbur.fintess_manager.core.utils.safeRunCatching
import ru.alexbur.fintess_manager.feature.clients.domain.repository.ClientTrainingsRepository

internal class ClientTrainingsInteractor(
    private val repository: ClientTrainingsRepository,
) {
    suspend fun getClientDetail(clientId: String) = safeRunCatching {
        repository.getClientDetail(clientId)
    }

    suspend fun getClientTrainings(clientId: String, date: String) = safeRunCatching {
        repository.getClientTrainings(clientId, date)
    }
}
