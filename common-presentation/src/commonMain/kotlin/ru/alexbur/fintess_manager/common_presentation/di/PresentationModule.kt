package ru.alexbur.fintess_manager.common_presentation.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.alexbur.fintess_manager.common_presentation.error_handler.FitnessManagerErrorHandler
import ru.alexbur.fintess_manager.common_presentation.error_handler.FitnessManagerErrorHandlerImpl
import ru.alexbur.fintess_manager.common_presentation.mediators.PreferenceMediator
import ru.alexbur.fintess_manager.common_presentation.mediators.PreferenceMediatorImpl

val presentationModule = module {
    singleOf(::FitnessManagerErrorHandlerImpl) { bind<FitnessManagerErrorHandler>() }
    singleOf(::PreferenceMediatorImpl) { bind<PreferenceMediator>() }
}