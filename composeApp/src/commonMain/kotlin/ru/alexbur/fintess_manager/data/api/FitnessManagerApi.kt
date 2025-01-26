package ru.alexbur.fintess_manager.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.alexbur.fintess_manager.data.models.RocketsResponse

internal class FitnessManagerApi(
    private val client: HttpClient,
) {

    suspend fun getRockets(): List<RocketsResponse> {
        return client.get("/v3/rockets").body()
    }
}