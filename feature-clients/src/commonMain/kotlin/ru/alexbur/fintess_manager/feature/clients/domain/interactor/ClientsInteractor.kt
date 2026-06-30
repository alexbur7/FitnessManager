package ru.alexbur.fintess_manager.feature.clients.domain.interactor

import ru.alexbur.fintess_manager.core.utils.safeRunCatching
import ru.alexbur.fintess_manager.feature.clients.domain.repository.ClientsRepository

internal class ClientsInteractor(
    private val repository: ClientsRepository,
) {
    suspend fun getClients(limit: Int, offset: Int) = safeRunCatching {
        repository.getClients(limit = limit, offset = offset)
    }
}
