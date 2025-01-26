package ru.alexbur.fintess_manager.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.alexbur.fintess_manager.core.AppLogger
import ru.alexbur.fintess_manager.domain.interactor.RocketsInteractor

class AppViewModel(
    private val interactor: RocketsInteractor
) : ViewModel() {

    private val _viewState = MutableStateFlow<AppViewState>(AppViewState.Initial)
    val viewState: StateFlow<AppViewState> = _viewState.asStateFlow()

    init {
        loadLeagues()
    }

    private fun loadLeagues() {
        viewModelScope.launch {
            interactor.getLeagues().onSuccess {
                _viewState.value = AppViewState.ShowRockets(it)
            }.onFailure {
                AppLogger.e("tut_error", "Error $it")
            }
        }
    }
}