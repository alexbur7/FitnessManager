package ru.alexbur.fintess_manager.feature.calendar.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.alexbur.fintess_manager.feature.calendar.presentation.CalendarViewModel

val calendarModule = module {
    viewModelOf(::CalendarViewModel)
}