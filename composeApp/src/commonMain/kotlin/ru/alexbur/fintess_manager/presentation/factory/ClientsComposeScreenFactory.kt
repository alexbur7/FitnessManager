package ru.alexbur.fintess_manager.presentation.factory

import androidx.navigation.NavGraphBuilder
import ru.alexbur.fintess_manager.feature.clients.presentation.detail.ClientTrainingsRoute
import ru.alexbur.fintess_manager.feature.clients.presentation.detail.ClientTrainingsScreenFactory
import ru.alexbur.fintess_manager.feature.clients.presentation.list.ClientsRoute
import ru.alexbur.fintess_manager.feature.clients.presentation.list.ClientsScreenFactory
import ru.alexbur.fintess_manager.navigation.Navigator

class ClientsComposeScreenFactory(
    private val builder: NavGraphBuilder,
) {

    fun create(navigator: Navigator) = with(builder) {
        create<ClientsRoute>(navigator, ClientsScreenFactory())
        // Регистрация в том же NavGraphBuilder, что и ClientsRoute — критично: у таба Clients свой
        // отдельный NavHost/NavController (MainScreen.kt TabContent), регистрация в app-level
        // AppComposeScreenFactory сделала бы route недостижимым из таба Clients (см. plan.md).
        create<ClientTrainingsRoute>(navigator, ClientTrainingsScreenFactory())
    }
}
