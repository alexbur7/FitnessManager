package ru.alexbur.fintess_manager.feature.clients.data.repository

import kotlinx.coroutines.withContext
import ru.alexbur.fintess_manager.core.DispatcherProvider
import ru.alexbur.fintess_manager.feature.clients.data.api.ClientsApi
import ru.alexbur.fintess_manager.feature.clients.data.mapper.ClientsMapper
import ru.alexbur.fintess_manager.feature.clients.domain.models.Client
import ru.alexbur.fintess_manager.feature.clients.domain.repository.ClientsRepository

internal class ClientsRepositoryImpl(
    private val api: ClientsApi,
    private val mapper: ClientsMapper,
    private val dispatcherProvider: DispatcherProvider,
) : ClientsRepository {

    override suspend fun getClients(limit: Int, offset: Int): Pair<List<Client>, Int> =
        withContext(dispatcherProvider.io()) {
            val response = api.getClients(limit = limit, offset = offset)
            response.clients.map { mapper.map(it) } to response.total
        }
}
