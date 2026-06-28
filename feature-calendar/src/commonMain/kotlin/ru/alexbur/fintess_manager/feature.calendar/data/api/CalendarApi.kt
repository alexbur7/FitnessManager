package ru.alexbur.fintess_manager.feature.calendar.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.alexbur.fintess_manager.feature.calendar.data.models.request.AppointmentsRequest
import ru.alexbur.fintess_manager.feature.calendar.data.models.response.AppointmentsResponse

internal class CalendarApi(
    private val client: HttpClient,
) {
    suspend fun getAppointments(request: AppointmentsRequest): AppointmentsResponse =
        client.post("calendar/appointments") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
}
