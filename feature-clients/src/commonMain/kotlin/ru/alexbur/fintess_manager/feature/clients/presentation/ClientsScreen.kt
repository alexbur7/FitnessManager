package ru.alexbur.fintess_manager.feature.clients.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import ru.alexbur.fintess_manager.common_presentation.mvi.ShowSnackBar
import ru.alexbur.fintess_manager.common_presentation.snackbar.LocalSnackbar
import ru.alexbur.fintess_manager.feature.clients.presentation.content.ClientsScreenContent
import ru.alexbur.fintess_manager.navigation.Navigator
import ru.alexbur.fintess_manager.navigation.Route
import ru.alexbur.fintess_manager.navigation.ScreenFactory

@Composable
internal fun ClientsScreen(
    @Suppress("UNUSED_PARAMETER") navigator: Navigator,
    viewModel: ClientsViewModel = koinViewModel(),
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val showSnackbar = LocalSnackbar.current
    LaunchedEffect(Unit) {
        viewModel.viewEvent.collect { event ->
            when (event) {
                is ShowSnackBar -> showSnackbar(event.settings)
            }
        }
    }
    ClientsScreenContent(
        state = state,
        onLoadNextPage = viewModel::loadNextPage,
        onRetry = viewModel::retry,
    )
}

@Serializable
data object ClientsRoute : Route

class ClientsScreenFactory : ScreenFactory<ClientsRoute> {

    @NonRestartableComposable
    @Composable
    override fun create(navigator: Navigator, route: ClientsRoute) {
        ClientsScreen(navigator)
    }
}
