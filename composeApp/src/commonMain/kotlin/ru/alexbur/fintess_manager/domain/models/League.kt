package ru.alexbur.fintess_manager.domain.models

data class League(
    val id: Long,
    val name: String,
    val type: String,
    val url: String
)
