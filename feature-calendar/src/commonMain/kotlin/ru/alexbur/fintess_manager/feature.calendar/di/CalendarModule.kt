package ru.alexbur.fintess_manager.feature.calendar.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.alexbur.fintess_manager.feature.calendar.data.api.CalendarApi
import ru.alexbur.fintess_manager.feature.calendar.data.mapper.CalendarMapper
import ru.alexbur.fintess_manager.feature.calendar.data.repository.CalendarRepositoryImpl
import ru.alexbur.fintess_manager.feature.calendar.domain.interactor.CalendarInteractor
import ru.alexbur.fintess_manager.feature.calendar.domain.repository.CalendarRepository
import ru.alexbur.fintess_manager.feature.calendar.presentation.CalendarViewModel

val calendarModule = module {
    singleOf(::CalendarApi)
    factoryOf(::CalendarMapper)
    factoryOf(::CalendarInteractor)
    factoryOf(::CalendarRepositoryImpl) { bind<CalendarRepository>() }
    viewModelOf(::CalendarViewModel)
}