package ru.alexbur.fintess_manager.feature.clients.domain.models

internal data class ClientsPage(
    val clients: List<Client>,
    val total: Int,
)
