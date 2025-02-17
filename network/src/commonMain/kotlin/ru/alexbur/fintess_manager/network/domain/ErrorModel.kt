package ru.alexbur.fintess_manager.network.domain

data class ErrorModel(
    override val message: String,
    val code: String,
) : Throwable(message)