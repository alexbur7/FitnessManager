package ru.alexbur.fintess_manager.feature.calendar.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.alexbur.fintess_manager.feature.calendar.data.models.response.AppointmentsResponse

internal class CalendarApi(
    private val client: HttpClient,
) {
    suspend fun getAppointments(): AppointmentsResponse =
        client.get("calendar/appointments").body()
}
