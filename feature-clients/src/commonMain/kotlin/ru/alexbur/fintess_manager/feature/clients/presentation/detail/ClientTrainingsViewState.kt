package ru.alexbur.fintess_manager.feature.clients.presentation.detail

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDate
import ru.alexbur.fintess_manager.common_presentation.mvi.ViewState
import ru.alexbur.fintess_manager.feature.clients.presentation.list.AvatarGradient

@Immutable
internal data class ClientTrainingsViewState(
    val clientName: String,
    val initials: String,
    val avatarGradient: AvatarGradient,
    val isDetailLoading: Boolean = true,
    val isDetailError: Boolean = false,
    val clientSinceText: String = "",
    val remainingWorkouts: Int = 0,
    val workoutsStatusText: String? = null,
    val weightText: String = "",
    val heightText: String = "",
    val ageText: String = "",
    val days: List<TrainingDay> = emptyList(),
    val selectedDayNumber: Int = 0,
    val sectionTitle: String = "",
    val trainings: List<ClientTrainingItem> = emptyList(),
    // Симметрично isDetailLoading: обе загрузки стартуют в init{} одинаково, асимметричный дефолт
    // допускал бы кратковременный показ "Тренировок нет" до того, как корутина выставит true.
    val isTrainingsLoading: Boolean = true,
    val isTrainingsError: Boolean = false,
) : ViewState

@Immutable
internal data class TrainingDay(
    val name: String,
    val number: Int,
    val isToday: Boolean,
    val date: LocalDate,
)

@Immutable
internal data class ClientTrainingItem(
    val id: Long,
    val timeRange: String,
    val subtitle: String,
    val status: TrainingStatus,
)

internal enum class TrainingStatus { Active, Soon }
