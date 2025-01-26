package ru.alexbur.fintess_manager.data.models

import kotlinx.serialization.Serializable

@Serializable
class RocketsResponse(
    val id: Long,
    val active: Boolean,
    val stages: Int,
    val country: String,
)