package ru.alexbur.fintess_manager.feature.clients.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ClientsResponse(
    @SerialName("clients")
    val clients: List<ClientResponse>,
    @SerialName("total")
    val total: Int,
)
