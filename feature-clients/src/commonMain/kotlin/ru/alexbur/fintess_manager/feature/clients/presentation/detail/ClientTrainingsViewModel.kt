package ru.alexbur.fintess_manager.feature.clients.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.alexbur.fintess_manager.common_presentation.EventFlow
import ru.alexbur.fintess_manager.common_presentation.MutableEventFlow
import ru.alexbur.fintess_manager.common_presentation.error_handler.FitnessManagerErrorHandler
import ru.alexbur.fintess_manager.feature.clients.domain.interactor.ClientTrainingsInteractor
import ru.alexbur.fintess_manager.feature.clients.domain.models.ClientDetail
import ru.alexbur.fintess_manager.feature.clients.domain.models.ClientTraining
import ru.alexbur.fintess_manager.feature.clients.domain.models.ClientTrainingStatus
import ru.alexbur.fintess_manager.feature.clients.presentation.list.AvatarGradient

internal class ClientTrainingsViewModel(
    private val clientId: String,
    initialName: String,
    initialInitials: String,
    initialAvatarGradient: AvatarGradient,
    private val interactor: ClientTrainingsInteractor,
    private val errorHandler: FitnessManagerErrorHandler,
) : ViewModel() {

    private val dayNames = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")

    private val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    private val monday = today.minus(today.dayOfWeek.ordinal, DateTimeUnit.DAY)

    // Без NextWeek/PreviousWeek (см. plan.md) неделя фиксирована на момент создания ViewModel.
    private val weekDays: List<TrainingDay> = (0..6).map { i ->
        val date = monday.plus(i, DateTimeUnit.DAY)
        TrainingDay(
            name = dayNames[i],
            number = date.dayOfMonth,
            isToday = date == today,
            date = date,
        )
    }

    private val _viewState = MutableStateFlow(
        ClientTrainingsViewState(
            clientName = initialName,
            initials = initialInitials,
            avatarGradient = initialAvatarGradient,
            days = weekDays,
            selectedDayNumber = today.dayOfMonth,
        ),
    )
    val viewState: StateFlow<ClientTrainingsViewState> = _viewState.asStateFlow()

    private val _viewEvent = MutableEventFlow()
    val viewEvent: EventFlow = _viewEvent

    private var trainingsJob: Job? = null

    init {
        loadClientDetail()
        loadTrainings(today)
    }

    fun obtainAction(action: ClientTrainingsAction) {
        when (action) {
            is ClientTrainingsAction.DaySelected -> selectDay(action.dayNumber)
            ClientTrainingsAction.Retry -> {
                _viewState.update { it.copy(isDetailLoading = true, isDetailError = false) }
                loadClientDetail()
            }

            ClientTrainingsAction.RetryTrainings -> {
                val date = weekDays.find { it.number == _viewState.value.selectedDayNumber }?.date ?: today
                loadTrainings(date)
            }
        }
    }

    private fun selectDay(dayNumber: Int) {
        val date = weekDays.find { it.number == dayNumber }?.date ?: return
        _viewState.update { it.copy(selectedDayNumber = dayNumber) }
        loadTrainings(date)
    }

    private fun loadClientDetail() {
        viewModelScope.launch {
            interactor.getClientDetail(clientId)
                .onSuccess { detail: ClientDetail ->
                    _viewState.update {
                        it.copy(
                            clientName = detail.name,
                            initials = computeInitials(detail.name),
                            isDetailLoading = false,
                            isDetailError = false,
                            clientSinceText = formatClientSince(detail.clientSince),
                            remainingWorkouts = detail.remainingWorkouts,
                            workoutsStatusText = detail.workoutsStatus,
                            weightText = formatWeight(detail.weightKg),
                            heightText = detail.heightCm.toString(),
                            ageText = detail.age.toString(),
                        )
                    }
                }
                .onFailure { error: Throwable ->
                    _viewState.update { it.copy(isDetailLoading = false, isDetailError = true) }
                    _viewEvent.send(errorHandler.handleError(error))
                }
        }
    }

    private fun loadTrainings(date: LocalDate) {
        // Race-guard: отменяем предыдущий in-flight запрос тренировок перед стартом нового — без
        // этого более медленный ответ на предыдущий день мог бы перезаписать state поверх более
        // свежего выбора дня (см. plan.md Decisions Made).
        trainingsJob?.cancel()
        trainingsJob = viewModelScope.launch {
            _viewState.update { it.copy(isTrainingsLoading = true, isTrainingsError = false) }
            interactor.getClientTrainings(clientId, date.toString())
                .onSuccess { trainings: List<ClientTraining> ->
                    val day = weekDays.find { it.date == date }
                    _viewState.update {
                        it.copy(
                            trainings = trainings.map { training -> training.toClientTrainingItem() },
                            sectionTitle = buildSectionTitle(
                                count = trainings.size,
                                isToday = day?.isToday ?: false,
                                dayName = day?.name.orEmpty(),
                            ),
                            isTrainingsLoading = false,
                            isTrainingsError = false,
                        )
                    }
                }
                .onFailure { error: Throwable ->
                    _viewState.update { it.copy(isTrainingsLoading = false, isTrainingsError = true) }
                    _viewEvent.send(errorHandler.handleError(error))
                }
        }
    }

    private fun ClientTraining.toClientTrainingItem() = ClientTrainingItem(
        id = id,
        timeRange = "$startTime — $endTime",
        subtitle = "$title - $durationMinutes минут",
        status = when (status) {
            ClientTrainingStatus.ACTIVE -> TrainingStatus.Active
            ClientTrainingStatus.SOON -> TrainingStatus.Soon
        },
    )
}
