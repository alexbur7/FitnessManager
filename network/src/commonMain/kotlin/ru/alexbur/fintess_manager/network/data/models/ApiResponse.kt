package ru.alexbur.fintess_manager.network.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
internal sealed class ApiResponse {

    @Serializable
    @SerialName("data")
    data class Success(val data: JsonElement) : ApiResponse()

    @Serializable
    @SerialName("error")
    data class Error(val error: JsonElement) : ApiResponse()
}

internal fun ApiResponse.getDataOrNull(): JsonElement? {
    return when (this) {
        is ApiResponse.Success -> this.data
        is ApiResponse.Error -> null
    }
}

internal fun ApiResponse.getErrorOrNull(): JsonElement? {
    return when (this) {
        is ApiResponse.Error -> this.error
        is ApiResponse.Success -> null
    }
}