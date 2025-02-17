package ru.alexbur.fintess_manager.network.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorModelResponse(
    @SerialName("message")
    val message: String,
    @SerialName("code")
    val code: String,
)