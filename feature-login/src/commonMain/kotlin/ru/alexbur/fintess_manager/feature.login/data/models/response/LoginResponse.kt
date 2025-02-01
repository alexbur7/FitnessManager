package ru.alexbur.fintess_manager.feature.login.data.models.response

import kotlinx.serialization.Serializable

@Serializable
internal class LoginResponse(
    val token: String
)