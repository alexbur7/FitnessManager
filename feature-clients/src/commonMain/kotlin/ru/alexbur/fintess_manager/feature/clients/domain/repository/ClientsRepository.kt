package ru.alexbur.fintess_manager.feature.clients.domain.repository

import ru.alexbur.fintess_manager.feature.clients.domain.models.ClientsPage

internal interface ClientsRepository {
    suspend fun getClients(limit: Int, offset: Int): ClientsPage
}
