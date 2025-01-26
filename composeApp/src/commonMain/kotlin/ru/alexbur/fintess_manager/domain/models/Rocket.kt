package ru.alexbur.fintess_manager.domain.models

data class Rocket(
    val id: Long,
    val active: Boolean,
    val stages: Int,
    val country: String,
)