package ru.alexbur.fintess_manager.feature.clients.domain.models

internal data class ClientTraining(
    val id: Long,
    val startTime: String,
    val endTime: String,
    val durationMinutes: Int,
    val title: String,
    val status: ClientTrainingStatus,
)

internal enum class ClientTrainingStatus {
    ACTIVE,
    SOON;

    companion object {
        fun create(value: String) = entries.find { it.name.equals(value, ignoreCase = true) } ?: SOON
    }
}
