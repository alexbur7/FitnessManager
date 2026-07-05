package ru.alexbur.fintess_manager.feature.clients.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.ParametersHolder
import ru.alexbur.fintess_manager.common_presentation.mvi.ShowSnackBar
import ru.alexbur.fintess_manager.common_presentation.snackbar.LocalSnackbar
import ru.alexbur.fintess_manager.feature.clients.presentation.detail.content.ClientTrainingsScreenContent
import ru.alexbur.fintess_manager.feature.clients.presentation.list.AvatarGradient
import ru.alexbur.fintess_manager.navigation.Navigator
import ru.alexbur.fintess_manager.navigation.Route
import ru.alexbur.fintess_manager.navigation.ScreenFactory

@Serializable
data class ClientTrainingsRoute(
    val clientId: String,
    val initialName: String,
    val initialInitials: String,
    val initialAvatarGradient: AvatarGradient,
) : Route

@Composable
internal fun ClientTrainingsScreen(
    navigator: Navigator,
    route: ClientTrainingsRoute,
    viewModel: ClientTrainingsViewModel = koinViewModel {
        ParametersHolder(
            mutableListOf(route.clientId, route.initialName, route.initialInitials, route.initialAvatarGradient),
        )
    },
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val showSnackbar = LocalSnackbar.current
    LaunchedEffect(Unit) {
        viewModel.viewEvent.collect { event ->
            if (event is ShowSnackBar) showSnackbar(event.settings)
        }
    }
    ClientTrainingsScreenContent(
        state = state,
        onAction = viewModel::obtainAction,
        onBackClick = navigator::popBackStack,
    )
}

class ClientTrainingsScreenFactory : ScreenFactory<ClientTrainingsRoute> {

    @Composable
    override fun create(navigator: Navigator, route: ClientTrainingsRoute) {
        ClientTrainingsScreen(navigator, route)
    }
}
