package ru.alexbur.fintess_manager.feature.calendar.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import ru.alexbur.fintess_manager.common_presentation.mvi.ShowSnackBar
import ru.alexbur.fintess_manager.common_presentation.snackbar.LocalSnackbar
import ru.alexbur.fintess_manager.feature.calendar.presentation.content.CalendarScreenContent
import ru.alexbur.fintess_manager.navigation.Navigator
import ru.alexbur.fintess_manager.navigation.Route
import ru.alexbur.fintess_manager.navigation.ScreenFactory

@Composable
internal fun CalendarScreen(
    @Suppress("UNUSED_PARAMETER") navigator: Navigator,
    viewModel: CalendarViewModel = koinViewModel(),
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
    CalendarScreenContent(
        state = state,
        onAction = viewModel::obtainAction,
        onPreviousWeek = { viewModel.obtainAction(CalendarAction.PreviousWeek) },
        onNextWeek = { viewModel.obtainAction(CalendarAction.NextWeek) },
    )
}

@Serializable
data object CalendarRoute : Route

class CalendarScreenFactory : ScreenFactory<CalendarRoute> {

    @NonRestartableComposable
    @Composable
    override fun create(
        navigator: Navigator,
        route: CalendarRoute
    ) {
        CalendarScreen(navigator)
    }
}