package ru.alexbur.fintess_manager.feature.login.data.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class LoginRequest(
    @SerialName("user_id")
    val userId: Long,
    @SerialName("otp")
    val otp: String
)