package ru.alexbur.fintess_manager.presentation.factory

import androidx.navigation.NavGraphBuilder
import ru.alexbur.fintess_manager.feature.clients.presentation.ClientsRoute
import ru.alexbur.fintess_manager.feature.clients.presentation.ClientsScreenFactory
import ru.alexbur.fintess_manager.navigation.Navigator

class ClientsComposeScreenFactory(
    private val builder: NavGraphBuilder,
) {

    fun create(navigator: Navigator) = with(builder) {
        create<ClientsRoute>(navigator, ClientsScreenFactory())
    }
}
