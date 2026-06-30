package ru.alexbur.fintess_manager.feature.clients.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import ru.alexbur.fintess_manager.feature.clients.data.models.response.ClientsResponse

internal class ClientsApi(
    private val client: HttpClient,
) {
    suspend fun getClients(limit: Int, offset: Int): ClientsResponse =
        client.get("relationships/clients") {
            parameter("limit", limit)
            parameter("offset", offset)
        }.body()
}
