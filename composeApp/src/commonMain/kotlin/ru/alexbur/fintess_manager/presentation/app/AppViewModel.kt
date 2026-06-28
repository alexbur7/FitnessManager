package ru.alexbur.fintess_manager.presentation.app

import androidx.lifecycle.ViewModel
import ru.alexbur.fintess_manager.common_presentation.mediators.PreferenceMediator
import ru.alexbur.fintess_manager.feature.login.presentation.navigation.LoginRoute
import ru.alexbur.fintess_manager.navigation.Route
import ru.alexbur.fintess_manager.presentation.main.navigation.MainRoute

internal class AppViewModel(
    private val preferenceMediator: PreferenceMediator,
) : ViewModel() {

    val startDestination: Route get() = if (preferenceMediator.accessToken.isNotEmpty()) MainRoute else LoginRoute
}