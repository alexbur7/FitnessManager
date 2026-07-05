package ru.alexbur.fintess_manager.feature.clients.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import ru.alexbur.fintess_manager.feature.clients.data.models.response.ClientDetailResponse
import ru.alexbur.fintess_manager.feature.clients.data.models.response.ClientTrainingsResponse

internal class ClientTrainingsApi(
    private val client: HttpClient,
) {
    suspend fun getClientDetail(clientId: String): ClientDetailResponse =
        client.get("relationships/clients/$clientId").body()

    suspend fun getClientTrainings(clientId: String, date: String): ClientTrainingsResponse =
        client.get("relationships/clients/$clientId/trainings") {
            parameter("date", date)
        }.body()
}
