package ru.alexbur.fintess_manager.feature.login.domain.models

internal data class Login(
    val accessToken: String,
    val refreshToken: String,
)