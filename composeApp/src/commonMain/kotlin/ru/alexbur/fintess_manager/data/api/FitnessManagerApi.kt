package ru.alexbur.fintess_manager.data.api

import io.ktor.client.HttpClient

internal class FitnessManagerApi(
    private val client: HttpClient,
)