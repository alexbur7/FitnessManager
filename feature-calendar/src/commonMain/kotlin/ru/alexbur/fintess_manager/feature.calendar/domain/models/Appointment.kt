package ru.alexbur.fintess_manager.feature.calendar.domain.models

internal data class Appointment(
    val id: Long,
    val dayNumber: Int,
    val time: String,
    val duration: String,
    val title: String,
    val clientName: String,
    val status: Status,
) {
    enum class Status {
        ACTIVE,
        SOON;

        companion object {
            fun create(value: String): Status {
                return entries.find { it.name.equals(value, true) } ?: SOON
            }
        }
    }
}