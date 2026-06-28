package ru.alexbur.fintess_manager.feature.calendar.data.repository

import kotlinx.coroutines.withContext
import ru.alexbur.fintess_manager.core.DispatcherProvider
import ru.alexbur.fintess_manager.feature.calendar.data.api.CalendarApi
import ru.alexbur.fintess_manager.feature.calendar.data.mapper.CalendarMapper
import ru.alexbur.fintess_manager.feature.calendar.domain.models.Appointment
import ru.alexbur.fintess_manager.feature.calendar.domain.repository.CalendarRepository

internal class CalendarRepositoryImpl(
    private val api: CalendarApi,
    private val mapper: CalendarMapper,
    private val dispatcherProvider: DispatcherProvider,
) : CalendarRepository {

    override suspend fun getAppointments(): List<Appointment> = withContext(dispatcherProvider.io()) {
        api.getAppointments().appointments.map { mapper.map(it) }
    }
}
