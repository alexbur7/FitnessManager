package ru.alexbur.fintess_manager.feature.clients.presentation

import androidx.compose.runtime.Immutable
import ru.alexbur.fintess_manager.common_presentation.mvi.ViewState

@Immutable
internal data class ClientsViewState(
    val clients: List<ClientItem>,
    val clientCount: Int,
    val isLoading: Boolean = false,
    val isLoadingNextPage: Boolean = false,
) : ViewState

@Immutable
internal data class ClientItem(
    val id: String,
    val initials: String,
    val name: String,
    val subtitle: String,
    val avatarGradient: AvatarGradient,
)

internal enum class AvatarGradient {
    Purple,
    RedOrange,
    GreenBlue,
    PurplePink,
}
