package ru.alexbur.fintess_manager.feature.clients.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.alexbur.fintess_manager.common_presentation.EventFlow
import ru.alexbur.fintess_manager.common_presentation.MutableEventFlow
import ru.alexbur.fintess_manager.common_presentation.error_handler.FitnessManagerErrorHandler
import ru.alexbur.fintess_manager.feature.clients.domain.interactor.ClientsInteractor
import ru.alexbur.fintess_manager.feature.clients.domain.models.Client

internal class ClientsViewModel(
    private val interactor: ClientsInteractor,
    private val errorHandler: FitnessManagerErrorHandler,
) : ViewModel() {

    private val limit = 20
    private var offset = 0
    private var hasMorePages = true

    private val _viewState = MutableStateFlow(
        ClientsViewState(
            clients = emptyList(),
            clientCount = 0,
            isLoading = true,
        ),
    )
    val viewState: StateFlow<ClientsViewState> = _viewState.asStateFlow()

    private val _viewEvent = MutableEventFlow()
    val viewEvent: EventFlow = _viewEvent

    init {
        loadClients()
    }

    fun loadNextPage() {
        if (!hasMorePages || _viewState.value.isLoadingNextPage || _viewState.value.isLoading) return
        offset += limit
        loadClients(isNextPage = true)
    }

    private fun loadClients(isNextPage: Boolean = false) {
        viewModelScope.launch {
            _viewState.update {
                if (isNextPage) it.copy(isLoadingNextPage = true)
                else it.copy(isLoading = true)
            }
            interactor.getClients(limit = limit, offset = offset)
                .onSuccess { (clients, total) ->
                    hasMorePages = clients.size == limit
                    val startIndex = if (isNextPage) _viewState.value.clients.size else 0
                    val newItems = clients.mapIndexed { i, client ->
                        client.toClientItem(index = startIndex + i)
                    }
                    _viewState.update { state ->
                        state.copy(
                            clients = if (isNextPage) state.clients + newItems else newItems,
                            clientCount = total,
                            isLoading = false,
                            isLoadingNextPage = false,
                        )
                    }
                }
                .onFailure { error ->
                    if (isNextPage) offset -= limit
                    _viewState.update { it.copy(isLoading = false, isLoadingNextPage = false) }
                    _viewEvent.send(errorHandler.handleError(error))
                }
        }
    }

    private fun Client.toClientItem(index: Int) = ClientItem(
        id = id,
        initials = name.split(" ").take(2).mapNotNull { it.firstOrNull()?.uppercaseChar() }.joinToString(""),
        name = name,
        subtitle = buildSubtitle(count = remainingWorkouts, status = workoutsStatus),
        avatarGradient = AvatarGradient.entries[index % AvatarGradient.entries.size],
    )

    private fun buildSubtitle(count: Int, status: String?): String {
        val word = when {
            count % 100 in 11..14 -> "тренировок"
            count % 10 == 1 -> "тренировка"
            count % 10 in 2..4 -> "тренировки"
            else -> "тренировок"
        }
        return if (status != null) "$count $word — $status" else "$count $word осталось"
    }
}
