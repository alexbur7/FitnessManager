package ru.alexbur.fintess_manager.feature.clients.presentation.list

import androidx.compose.runtime.Immutable
import ru.alexbur.fintess_manager.common_presentation.mvi.ViewState

@Immutable
internal data class ClientsViewState(
    val clients: List<ClientItem>,
    val clientCount: Int,
    val isLoading: Boolean = false,
    val isLoadingNextPage: Boolean = false,
    val isError: Boolean = false,
) : ViewState

@Immutable
internal data class ClientItem(
    val id: String,
    val initials: String,
    val name: String,
    val subtitle: String,
    val avatarGradient: AvatarGradient,
)

// Public (not internal): referenced by the public ClientTrainingsRoute (ClientTrainingsScreen.kt),
// which composeApp needs to construct — a public Route cannot expose an internal-typed property.
enum class AvatarGradient {
    Purple,
    RedOrange,
    GreenBlue,
    PurplePink,
}
