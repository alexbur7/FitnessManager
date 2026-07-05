package ru.alexbur.fintess_manager.feature.clients.presentation.detail

internal sealed class ClientTrainingsAction {
    class DaySelected(val dayNumber: Int) : ClientTrainingsAction()
    data object Retry : ClientTrainingsAction()
    data object RetryTrainings : ClientTrainingsAction()
}
